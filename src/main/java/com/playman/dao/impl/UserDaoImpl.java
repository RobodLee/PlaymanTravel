package com.playman.dao.impl;

import com.playman.dao.UserDao;
import com.playman.entity.User;
import com.playman.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //通过username从数据库中查询是否注册过了，注册过了返回true，反之返回false
    @Override
    public User findUserByUsername(String username) {
        //如果没有从数据库中查到相应数据就会报异常
        User user = null;
        try {
            String sql = "select * from user where username = ?";
            user = template.queryForObject(sql , new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {
           // e.printStackTrace();
        }
        return user;
    }

    //将用户信息保存在数据库中
    @Override
    public boolean saveUser(User user) {
        try {
            String sql = "insert into user(username , password , name , birthday , sex , telephone , email , status , code) values(?,?,?,?,?,?,?,?,?)";
            template.update(sql,user.getUsername(),
                                user.getPassword(),
                                user.getName(),
                                user.getBirthday(),
                                user.getSex(),
                                user.getTelephone(),
                                user.getEmail(),
                                user.getStatus(),
                                user.getCode()
                                );
            System.out.println(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //更改用户的激活状态，将status属性由N改为Y
    @Override
    public boolean updateStatus(String code) {
        try {
            String sql = "update user set status = 'Y' where code = ?";
            template.update(sql,code);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public User findUserByCode(String code) {
        User user = null;
        try {
            String sql = "select * from USER where code = ? ;";
            user = template.queryForObject(sql , new BeanPropertyRowMapper<User>(User.class), code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            String sql = "select * from USER where username = ? and password = ? ;";
            user = template.queryForObject(sql , new BeanPropertyRowMapper<User>(User.class) ,username , password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}
