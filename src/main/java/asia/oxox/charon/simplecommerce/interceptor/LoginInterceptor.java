package asia.oxox.charon.simplecommerce.interceptor;

import asia.oxox.charon.simplecommerce.utils.UserHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: simple-commerce
 * @description: 登录拦截器
 * @author: Charon
 * @create: 2023-04-29 23:15
 **/
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取用户
        if (UserHolder.getUser() == null) {
            //不存在用户 拦截
            response.setStatus(401);
            return false;
        }
        //存在用户放行
        return true;
    }


}