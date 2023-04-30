package asia.oxox.charon.simplecommerce.utils;

import asia.oxox.charon.simplecommerce.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @program: simple-commerce
 * @description: redis分布式id生成器
 * @author: Charon
 * @create: 2023-04-30 01:39
 **/
@Component
public class RedisIdGenerator {

    /**
     * 初始时间戳-从2023-01-01 00:00:00开始
     */
    private static final Long BEGIN_TIMESTAMP = 1672502400L;

    /**
     * 序列号位数
     */
    private static final Integer COUNT_BITS = 32;

    private final RedisService redisService;

    public RedisIdGenerator(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * 获取id
     *
     * @param keyPrefix 业务前缀
     * @return {@link Long}
     */
    public Long nextId(String keyPrefix) {
        //生成时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp = nowSecond - BEGIN_TIMESTAMP;
        //生成序列号
        //生成当前日期 精确到天
        String today = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        //自增长
        Long count = redisService.incr("icr:" + keyPrefix + ":" + today);
        //拼接并返回
        return timestamp << COUNT_BITS | count;
    }

}
