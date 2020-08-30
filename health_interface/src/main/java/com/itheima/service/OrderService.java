package com.itheima.service;

import com.itheima.pojo.Order;

import java.util.Map;

/**
 * @Author ${LiJian}
 * @Date 20:15 2020/8/27
 */
public interface OrderService {
    /**
     * 提交预约信息
     * @param map
     * @return
     */
    Order submitOrder(Map<String, String> map);

    /**
     * 通过预约成功id查询
     * @param id
     * @return
     */
    Map<String, Object> findById(int id);
}
