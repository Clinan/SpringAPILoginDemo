package com.example.springbootdemo.service.impl;

import com.example.springbootdemo.model.User;
import com.example.springbootdemo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author lachen
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getByUserId(Long userId) {
        // FIXME 在这里根据用户ID查询用户
        User user = new User();
        user.setId(userId);
        user.setUsername("克林");
        return user;
    }
}
