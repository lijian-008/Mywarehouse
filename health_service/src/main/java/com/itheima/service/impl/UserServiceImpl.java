package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.UserDao;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.soap.SOAPBinding;

/**
 * @Author ${LiJian}
 * @Date 18:11 2020/8/29
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public User getName(String username) {
        return userDao.getName(username);
    }
}
