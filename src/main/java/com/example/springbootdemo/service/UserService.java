package com.example.springbootdemo.service;

import com.example.springbootdemo.model.User;

/**
 * @author lachen
 */
public interface UserService {

    public User getByUserId(Long userId);
}
