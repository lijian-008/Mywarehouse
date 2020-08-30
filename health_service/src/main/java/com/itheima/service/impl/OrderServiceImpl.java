package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.exception.HealthException;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author ${LiJian}
 * @Date 20:17 2020/8/27
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;

    /**
     * 提交预约
     * @param map
     * @return
     */
    @Override
    public Order submitOrder(Map<String, String> map)throws HealthException {
        //通过日期查询预约是否存在
        String orderDateStr = map.get("orderDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //查询到的日期格式化
        Date date=null;
        try {
            date = sdf.parse(orderDateStr);
        } catch (ParseException e) {
            //e.printStackTrace();
            throw  new HealthException("日期格式转换异常");
        }
        //通过转换后的日期查询可预约数与以预约数
        OrderSetting orderDate = orderSettingDao.findByOrderDate(date);
        //开始判断
        if (null==orderDate) {
            //如果不存在则报错
            throw new HealthException("今日无可预约套餐");
        }
        //如果可以预约，则判断可预约数是否大于以预约数
        if (orderDate.getReservations()>=orderDate.getNumber()) {
            //如果可预约数小于以预约数则报错
            throw new HealthException("今日预约已满");
        }
        //2.判断是否为会员，可以通过手机号查询
        String telephone = map.get("telephone");
        //查询出会员信息
        Member member = memberDao.findByTelephone(telephone);
        if (null==member) {
            //不是会员，则添加信息
            //创建会员对象
            member = new Member();
            member.setName(map.get("name"));
            member.setSex(map.get("sex"));
            member.setIdCard(map.get("idCard"));
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);

            memberDao.add(member);
        }
        //不能重复预约
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(date);
        order.setSetmealId(Integer.valueOf(map.get("setmealId")));
        //查询出所有已预约的信息
        List<Order> orderList = orderDao.findByCondition(order);

        //判断
        if (null!=orderList && orderList.size()>0) {
            //不为空，且大于零则报错
            throw new HealthException("不能重复预约");
        }
        //没有重复则添加预约信息
        order.setOrderType(map.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        orderDao.add(order);

        //4.更新可预约数量
        orderSettingDao.editReservationsByOrderDate(orderDate);
        return order;
    }

    /**
     * 通过预约成功的id查询
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> findById(int id) {
        return orderDao.findById4Detail(id);
    }
}
