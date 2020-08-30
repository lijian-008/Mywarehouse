package com.itheima.dao;

import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    /**
     * 添加预约信息
     * @param order
     */
    void add(Order order);

    /**
     * 查询所有预约信息
     * @param order
     * @return
     */
    List<Order> findByCondition(Order order);

    /**
     * 通过预约成功的id查询
     * @param id
     * @return
     */
    Map findById4Detail(Integer id);

    Integer findOrderCountByDate(String date);
    Integer findOrderCountAfterDate(String date);
    Integer findVisitsCountByDate(String date);
    Integer findVisitsCountAfterDate(String date);
    List<Map> findHotSetmeal();
}
