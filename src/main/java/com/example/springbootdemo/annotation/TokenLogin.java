package com.example.springbootdemo.annotation;

import java.lang.annotation.*;

/**
 * 用于在方法上注解用户是否登录，如果加了这个注解，该方法会判断当前请求使用有用户登录了
 *
 * @author lachen
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenLogin {

}
