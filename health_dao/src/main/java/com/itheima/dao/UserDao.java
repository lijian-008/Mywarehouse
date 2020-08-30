package com.itheima.dao;

import com.itheima.pojo.User;

/**
 * @Author ${LiJian}
 * @Date 19:39 2020/8/29
 */
public interface UserDao {
    /**
     * 通过用户名查询信息
     * @param username
     * @return
     */
    User getName(String username);
}
