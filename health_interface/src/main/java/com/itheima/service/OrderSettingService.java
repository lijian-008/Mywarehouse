package com.itheima.service;

import com.itheima.exception.HealthException;
import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @Author ${LiJian}
 * @Date 19:27 2020/8/25
 */

public interface OrderSettingService {
    /**
     * 批量导入信息
      * @param orderSettinglist
     */
    void add(List<OrderSetting> orderSettinglist)throws HealthException;

    /**
     * 通过日期查询可预约数，以预约数
     * @param month
     * @return
     */
    List<Map> getOrderSettingByMonth(String month);

    /**
     * 预约设置
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting)throws HealthException;
}
