package com.example.springbootdemo.annotation;

import java.lang.annotation.*;

/**
 * 用于参数，用于和TokenLogin配套使用，用户登录的情况下，需要注入用户所需的声明注解
 *
 * @author lachen
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser {
}
