package com.example.springbootdemo.config;

import com.example.springbootdemo.interceptor.LoginInterceptor;
import com.example.springbootdemo.resolver.LoginUserResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 *
 * @author lachen
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private LoginUserResolver loginUserResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，指定要拦截的url匹配规则为api开头的请求
        registry.addInterceptor(loginInterceptor).addPathPatterns("/api/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserResolver);
    }
}
