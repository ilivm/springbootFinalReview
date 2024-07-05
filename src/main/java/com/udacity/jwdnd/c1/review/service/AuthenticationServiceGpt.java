package com.udacity.jwdnd.c1.review.service;

import com.udacity.jwdnd.c1.review.mapper.UserMapper;
import com.udacity.jwdnd.c1.review.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceGpt {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserMapper userMapper;

    public AuthenticationServiceGpt(BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    public boolean authenticate(String username, String password) {
        User user = userMapper.getUser(username);
        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return true;
        } else {
            return false;
        }
    }
}
