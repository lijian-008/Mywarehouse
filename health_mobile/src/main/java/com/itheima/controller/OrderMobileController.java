package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.StringUtil;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @Author ${LiJian}
 * @Date 20:09 2020/8/27
 */
@RestController
@RequestMapping("/order")
public class OrderMobileController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    /**
     * 提交预约信息
     * @param map
     * @return
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String,String>map){
        //创建jedis对象
        Jedis jedis = jedisPool.getResource();
        //获得手机号码
        String telephone = map.get("telephone");
        //获取redis中验证码的key
        String key = RedisMessageConstant.SENDTYPE_ORDER+":"+ telephone;
        //判断是否存在验证码
        String yzm= jedis.get(key);
        if (StringUtil.isEmpty(yzm)) {
            //没有值，则重新获取
            return new Result(false, MessageConstant.VALIDATECODE_ERRORA);
        }
        //获取页面的验证码
        String validateCode = map.get("validateCode");
        //判断数据库是否有验证码
        if (!yzm.equals(validateCode)) {
            //验证码不同，则报错
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
        //清楚数据库中的验证码，防止刻意骚扰
        jedis.del(key);
        //预约类型
        map.put("orderType", Order.ORDERTYPE_WEIXIN);
        //预约成功后需要展示页面信息，所以返回id
        Order order = orderService.submitOrder(map);

        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS,order);
    }

    /**
     * 通过预约成功的id查询
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(int id){
        //查询到的信息分装到map集合中
        Map<String, Object> map = orderService.findById(id);
        return new Result(true,MessageConstant.GET_SETMEAL_LIST_SUCCESS,map);
    }
}
