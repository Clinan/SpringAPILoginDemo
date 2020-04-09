package com.example.springbootdemo.resolver;

import com.example.springbootdemo.annotation.LoginUser;
import com.example.springbootdemo.interceptor.LoginInterceptor;
import com.example.springbootdemo.model.User;
import com.example.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 注入在bean的方法中使用{@code @LoginUser}注解的参数
 *
 * @author lachen
 */
@Component
public class LoginUserResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        // 检查参数类型并判断注解，只有符合并返回true,才会执行下面解析参数的方法
        return methodParameter.getParameterType().isAssignableFrom(User.class)
                && methodParameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory)
            throws Exception {
        // 获取作用域仅为request中的UserId参数，这个参数是再interceptor塞进去的
        Object attribute = nativeWebRequest.getAttribute(LoginInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if (attribute == null) {
            return null;
        }
        // 根据userId查询用户信息 注入到方法的参数上
        return userService.getByUserId((Long) attribute);
    }
}
