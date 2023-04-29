package asia.oxox.charon.simplecommerce.service.impl;

import asia.oxox.charon.simplecommerce.entity.DO.User;
import asia.oxox.charon.simplecommerce.entity.DTO.UserLoginDto;
import asia.oxox.charon.simplecommerce.enums.ResultCodeEnum;
import asia.oxox.charon.simplecommerce.exception.BizException;
import asia.oxox.charon.simplecommerce.mapper.UserMapper;
import asia.oxox.charon.simplecommerce.service.RedisService;
import asia.oxox.charon.simplecommerce.service.UserService;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    private RedisService redisService;

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
        String token = UUID.randomUUID().toString(true);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);

        //将token：id存入redis
        String key = LOGIN_USER_KEY + token;
        redisService.set(key, user.getId(), LOGIN_USER_TTL);
        return map;
    }
}




