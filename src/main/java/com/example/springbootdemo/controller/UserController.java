package com.example.springbootdemo.controller;

import com.example.springbootdemo.annotation.LoginUser;
import com.example.springbootdemo.annotation.TokenLogin;
import com.example.springbootdemo.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lachen
 */
@RequestMapping("api/user/")
@RestController
public class UserController {

    @RequestMapping("test")
    @TokenLogin
    public String testLogin(@LoginUser User user) {
        return "user[" + user.getUsername() + "]is login";
    }

}
