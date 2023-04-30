package asia.oxox.charon.simplecommerce.utils;

import asia.oxox.charon.simplecommerce.service.RedisService;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static asia.oxox.charon.simplecommerce.constants.RedisConstants.CACHE_NULL_TTL;

/**
 * @program: graduate-website
 * @description: 缓存工具类
 * @author: Charon
 * @create: 2023-03-27 13:42
 **/

@Component
@AllArgsConstructor
public class CacheClient {

    private RedisService redisService;
    private StringRedisTemplate stringRedisTemplate;

    public <T> void set(String key, T value, Long time, TimeUnit unit) {
        redisService.set(key, value, time, unit);
        //stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }

    /**
     * 获取锁
     */
    public boolean tryLock(String key) {
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 4, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag);
    }

    /**
     * 释放锁
     */
    public void unlock(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * redis实现缓存-利用互斥锁解决缓存击穿
     *
     * @param keyPrefix
     * @param id
     * @param type
     * @param dbFallback
     * @param time
     * @param unit
     * @param <T>
     * @param <ID>
     * @return
     */
    public <T, ID> T queryWithMutex(String keyPrefix, String lockKeyPrefix, ID id, Class<T> type, Function<ID, T> dbFallback, Long time, TimeUnit unit) {
        //1.从redis查询对应对象缓存
        String key = keyPrefix + id;
        String json = stringRedisTemplate.opsForValue().get(key);

        //2.判断是否存在
        if (StrUtil.isNotBlank(json)) {
            //3.存在，直接返回
            return JSONUtil.toBean(json, type);
        }
        //判断命中是否是空值
        if (json != null) {
            //返回错误信息
            return null;
        }

        //4.实现缓存重建
        //4.1 获取互斥锁
        String lockKey = lockKeyPrefix + id;
        T t = null;
        try {
            boolean isLock = tryLock(lockKey);
            //4.2 是否获取成功
            if (!isLock) {
                //4.3 失败，休眠并重试
                Thread.sleep(50);
                return queryWithMutex(keyPrefix, lockKeyPrefix, id, type, dbFallback, time, unit);
            }

            //4.5 成功，根据id查询数据库
            t = dbFallback.apply(id);
            //缓存穿透的情况-这里缓存空值
            if (t == null) {
                //将空值写入redis
                stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
                //5.不存在，返回错误
                return null;
            }

            //6.存在，写入redis
            this.set(key, t, time, unit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //7.释放互斥锁
            unlock(lockKey);
        }
        //8.返回
        return t;
    }

}
