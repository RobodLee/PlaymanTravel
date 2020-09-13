package com.playman.service;

import com.playman.entity.User;

public interface UserService {

    //用户注册
    boolean register(User user);

    //激活用户
    boolean activate(String code);

    User login(User user);
}
