package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.exception.HealthException;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ${LiJian}
 * @Date 19:27 2020/8/25
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    /**
     * 批量加入文件
     * @param orderSettinglist
     * @throws HealthException
     */
    @Override
    @Transactional
    public void add(List<OrderSetting> orderSettinglist) throws HealthException {
        //遍历集合
        for (OrderSetting setting : orderSettinglist) {
            //判断数据库是否存在日期了，通过日期查询
           OrderSetting osIndB = orderSettingDao.findByOrderDate(setting.getOrderDate());
            if (null!=osIndB) {
                //数据库存在预约，以预约数不能大于可预约数
                if (osIndB.getReservations()>setting.getNumber()) {
                    //大于，则抛异常
                    throw new HealthException(setting.getOrderDate()+"中，已预约数不能大于可预约数量");
                }
                orderSettingDao.updateNumber(setting);
            }else {
                //如果没有这个日期，则添加
                orderSettingDao.add(setting);
            }
        }
    }

    /**
     * 通过日期查询可预约数，以预约数
     * @param month
     * @return
     */
    @Override
    public List<Map> getOrderSettingByMonth(String month) {
        //
        String dateBegin=month+"-1";//表示月份开始的时间
        String dateEnd=month+"-31";//表示月份结束的时间
        //再把日期分装到map中
        Map map = new HashMap();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        //通过日，查询出所有月份可预约数与以预约数
        List<OrderSetting>list = orderSettingDao.getOrderSettingByMonth(map);
        //把查询到的所有数据分装到list<map>中返回
        List<Map> data = new ArrayList<>();
        //遍历出查询到的数据
        for (OrderSetting orderSetting : list) {
            Map ord = new HashMap();
            ord.put("date",orderSetting.getOrderDate().getDate());//获取几号
            ord.put("number",orderSetting.getNumber());
            ord.put("reservations",orderSetting.getReservations());
            data.add(ord);
        }
        return data;
    }

    /**
     * 预约设置
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting)throws HealthException {
        //通过日期查询可预约数与已预约数
        OrderSetting osIndB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        //判断预约数与可预约数
        if (null!=osIndB) {
            //如果已预约数大于可预约数，则报错
            if (osIndB.getReservations()>osIndB.getNumber()) {
                throw new HealthException("可预约数不能小于以预约数");
            }
            //如果以预约数小于可预约数，则更新
            orderSettingDao.updateNumber(orderSetting);
        }else {
            //如果没有，则添加
            orderSettingDao.add(orderSetting);
        }
    }
}
