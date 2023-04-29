package asia.oxox.charon.simplecommerce.interceptor;

import asia.oxox.charon.simplecommerce.constants.RedisConstants;
import asia.oxox.charon.simplecommerce.entity.DO.User;
import asia.oxox.charon.simplecommerce.service.RedisService;
import asia.oxox.charon.simplecommerce.service.UserService;
import asia.oxox.charon.simplecommerce.utils.UserHolder;
import cn.hutool.core.lang.Opt;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static asia.oxox.charon.simplecommerce.constants.RedisConstants.CACHE_USER_KEY;
import static asia.oxox.charon.simplecommerce.constants.RedisConstants.CACHE_USER_TTL;

/**
 * @program: simple-commerce
 * @description: 刷新令牌拦截器
 * @author: Charon
 * @create: 2023-04-29 22:58
 **/
@AllArgsConstructor
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private final RedisService redisService;
    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头中获取token
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            //不存在token
            return true;
        }
        //从redis中获取用户id
        Long userId = (Long) redisService.get(RedisConstants.LOGIN_USER_KEY + token);
        //用户不存在
        if (Objects.isNull(userId)) {
            return true;
        }
        //查询redis中是否有该用户缓存信息，如果没有，则查询数据库
        User user = Opt.ofNullable((User) redisService.get(CACHE_USER_KEY + userId)).orElseGet(() -> {
            User u = userService.getById(userId);
            redisService.set(CACHE_USER_KEY + userId, u, CACHE_USER_TTL, TimeUnit.MINUTES);
            return u;
        });
        //存入ThreadLocal
        UserHolder.saveUser(user);
        //token续命
        redisService.expire(RedisConstants.LOGIN_USER_KEY + token, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
