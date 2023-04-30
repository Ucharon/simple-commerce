package asia.oxox.charon.simplecommerce.config;

import asia.oxox.charon.simplecommerce.interceptor.LoginInterceptor;
import asia.oxox.charon.simplecommerce.interceptor.RefreshTokenInterceptor;
import asia.oxox.charon.simplecommerce.service.RedisService;
import asia.oxox.charon.simplecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

    private RedisService redisService;
    private UserService userService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登陆拦截器
        registry
                .addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/user/login"
                )
                .order(1);
        //Token续命拦截器
        registry
                .addInterceptor(new RefreshTokenInterceptor(redisService, userService))
                .addPathPatterns("/**")
                .order(0);
    }
}
