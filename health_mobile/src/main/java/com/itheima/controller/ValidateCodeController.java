package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author ${LiJian}
 * @Date 18:21 2020/8/27
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    /**
     * 注入redis数据库
     */
    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/send4Order")
    public Result send4Order(String telephone){
        //创建redis数据库,
        Jedis jedis = jedisPool.getResource();
        String key = RedisMessageConstant.SENDTYPE_ORDER +":"+ telephone;
        //通过key获取验证码
        String codeRedis = jedis.get(key);
        //判断是否有验证码
        if (null==codeRedis) {
            //如果为空则创建验证码
            Integer code = ValidateCodeUtils.generateValidateCode(6);
            //发送验证码
            try {
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code+"");
                //设置验证验证码存活时间
                jedis.setex(key,10*60,code+"");
                //发送成功
                return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
            } catch (ClientException e) {
                e.printStackTrace();
                return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
            }
        }
        //验证码以存在
        return new Result(false, MessageConstant.VALIDATECODE);
    }
    @PostMapping("/send4Login")
    public Result login(String telephone){
        //创建jedis对象
        Jedis jedis = jedisPool.getResource();
        //设置key
        String loginKey = RedisMessageConstant.SENDTYPE_LOGIN+":"+telephone;
        //获取key值，判断是否有验证码
        String code = jedis.get(loginKey);
        //判断
        if (null==code) {
            //如果不存在则创建
            Integer integer = ValidateCodeUtils.generateValidateCode(6);
            //发送到手机
            try {
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,integer+"");
                //发送成功后设置验证码存在时间
                jedis.setex(loginKey,5*60,integer+"");

                //返回结果
                return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
            } catch (ClientException e) {
                e.printStackTrace();
                //
                return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
            }
        }
        return new Result(false,MessageConstant.VALIDATECODE);
    }
}
