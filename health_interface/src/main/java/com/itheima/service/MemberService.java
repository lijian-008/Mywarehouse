package com.itheima.service;

import com.itheima.pojo.Member;

/**
 * @Author ${LiJian}
 * @Date 11:55 2020/8/28
 */

public interface MemberService {
    /**
     * 查询会员信息
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 添加会员信息
     * @param member
     */
    void add(Member member);
}
