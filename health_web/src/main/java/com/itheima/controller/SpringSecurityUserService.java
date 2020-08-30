package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author ${LiJian}
 * @Date 18:05 2020/8/29
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过用户名查询所有用户信息
        User user = userService.getName(username);
        //判断
        if (null!=user) {
            //获取数据库密码
            String password = user.getPassword();
            //创建储存所有权限的集合
            List<GrantedAuthority> authorities = new ArrayList<>();

            SimpleGrantedAuthority sty = null;
            //获取所有角色的集合，授权
            Set<Role> roles = user.getRoles();
            //判断
            if (null!=roles) {
                //遍历出所有的角色
                for (Role role : roles) {
                    sty = new SimpleGrantedAuthority(role.getKeyword());
                    //添加到权限集合
                    authorities.add(sty);

                    //通过角色，查询所有权限集合
                    Set<Permission> permissions = role.getPermissions();
                    if (null!= permissions) {
                        //遍历出所有权限
                        for (Permission permission : permissions) {
                            sty = new SimpleGrantedAuthority(permission.getKeyword());
                            //添加到集合
                            authorities.add(sty);
                        }
                    }
                }
            }
            //可以反问
            return new org.springframework.security.core.userdetails.User(username, password,authorities);
        }
        //访问失败
        return null;
    }
}
