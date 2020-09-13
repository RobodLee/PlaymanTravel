package com.playman.service.impl;

import com.playman.dao.UserDao;
import com.playman.dao.impl.UserDaoImpl;
import com.playman.entity.User;
import com.playman.service.UserService;
import com.playman.util.MailUtils;
import com.playman.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoImpl();

    @Override
    public boolean register(User user) {
        User registerUser =dao.findUserByUsername(user.getUsername());
        if (registerUser == null) {
            user.setStatus("N");
            String code = UuidUtil.getUuid();
            user.setCode(code);
            if (dao.saveUser(user)) {
                String emailContent = "<a href=http://127.0.0.1:8080/playmantravel/user/activate?code="+code+">点击激活【迪哥旅游网】</a>";
                MailUtils.sendMail(user.getEmail() , emailContent , "激活邮件");
                return true;
            }
        }
        return false;
    }

    //激活用户
    @Override
    public boolean activate(String code) {
        User user = dao.findUserByCode(code);
        if (user != null) {
            return dao.updateStatus(code);
        }
        return false;
    }

    @Override
    public User login(User user) {
        return dao.findUserByUsernameAndPassword(user.getUsername() , user.getPassword());
    }

}
