package asia.oxox.charon.simplecommerce.service.impl;

import asia.oxox.charon.simplecommerce.entity.DTO.UserLoginDto;
import asia.oxox.charon.simplecommerce.enums.ResultCodeEnum;
import asia.oxox.charon.simplecommerce.exception.BizException;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import asia.oxox.charon.simplecommerce.entity.DO.User;
import asia.oxox.charon.simplecommerce.service.UserService;
import asia.oxox.charon.simplecommerce.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static asia.oxox.charon.simplecommerce.constants.RedisConstants.LOGIN_USER_KEY;
import static asia.oxox.charon.simplecommerce.constants.RedisConstants.LOGIN_USER_TTL;

/**
 * @author charon
 * @description 针对表【sys_user】的数据库操作Service实现
 * @createDate 2023-04-29 22:02:07
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private StringRedisTemplate redisTemplate;

    @Override
    public Map<String, String> login(UserLoginDto userDto) {
        //根据用户名查询用户，错误则直接返回错误
        User user = Optional.ofNullable(lambdaQuery()
                .eq(User::getUserName, userDto.getUserName())
                .one()).orElseThrow(() -> new BizException(ResultCodeEnum.LOGIN_ERROR));

        //验证密码
        if (!DigestUtil.md5Hex(userDto.getPassword()).equals(user.getPassword())) {
            throw new BizException(ResultCodeEnum.LOGIN_ERROR);
        }

        //生成token
        String token = UUID.randomUUID().toString();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        //将token：id存入redis
        String key = LOGIN_USER_KEY + token;
        redisTemplate.opsForValue().set(key, String.valueOf(user.getId()), LOGIN_USER_TTL, TimeUnit.MINUTES);

        return map;
    }
}




