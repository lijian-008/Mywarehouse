package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author ${LiJian}
 * @Date 19:28 2020/8/25
 */
public interface OrderSettingDao {
    /**
     * 通过日期查询预约设置
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 更新预约设置
     * @param setting
     */
    void updateNumber(OrderSetting setting);

    /**
     * 添加预约设置
     * @param setting
     */
    void add(OrderSetting setting);

    /**
     * 查询可以预约数与以预约数
     * @param map
     * @return
     */
    List<OrderSetting> getOrderSettingByMonth(Map map);

    /**
     * 更新以预约人数
     * @param orderDate
     */
    void editReservationsByOrderDate(OrderSetting orderDate);
}
