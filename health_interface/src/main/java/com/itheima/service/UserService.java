package com.itheima.service;

import com.itheima.pojo.User;

/**
 * @Author ${LiJian}
 * @Date 18:10 2020/8/29
 */
public interface UserService {
    /**
     * 通过用户名查询所有信息
     * @param username
     * @return
     */
    User getName(String username);
}
