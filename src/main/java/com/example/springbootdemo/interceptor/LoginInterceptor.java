package com.example.springbootdemo.interceptor;

import com.example.springbootdemo.annotation.TokenLogin;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器，如果controller上添加了注解@TokenLogin的，当前用户没有登录，则会返回500
 *
 * @author lachen
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    public static final String TOKEN_KEY = "token";
    public static final String USER_KEY = "userId";


    /**
     * @return 返回true 就可以跳转刀controller 否则返回500
     * @throws Exception exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TokenLogin annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(TokenLogin.class);
        } else {
            return true;
        }
        if (annotation == null) {
            return true;
        }

        // 先从请求头中获取token 如果没有 则从参数中获取
        String token = request.getHeader(TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(TOKEN_KEY);
        }

        if (StringUtils.isBlank(token)) {
            // FIXME 这里要有全局异常捕获类去捕获这个异常
            throw new RuntimeException("Token不能为空");
        }
        // FIXME 通过token查找userId 例如从redis中查找
        Long userId = getUserIdByToken(token);
        request.setAttribute(USER_KEY, userId);
        return true;
    }

    /**
     * 模拟通过token获取userId
     *
     * @param token token
     * @return userId
     */
    private Long getUserIdByToken(String token) {
        switch (token) {
            case "1":
                return 1L;
            case "2":
                return 2L;
            case "3":
                return 3L;
            case "4":
                return 4L;
            default:
                return 0L;
        }
    }
}
