package com.playman.dao;

import com.playman.entity.User;

public interface UserDao {

    //通过username从数据库中查询是否注册过了，注册过了返回true，反之返回false
    User findUserByUsername(String username);

    //将用户信息保存在数据库中
    boolean saveUser(User user);

    //更改用户的激活状态，将status属性由N改为Y
    boolean updateStatus(String code);

    //根据Code去查询用户是否存在
    User findUserByCode(String code);

    //通过用户名和密码从服务器中得到一个完整的用户信息
    User findUserByUsernameAndPassword(String username, String password);
}
