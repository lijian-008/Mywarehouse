package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;


/**
 * @Author ${LiJian}
 * @Date 15:27 2020/8/28
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    /**
     * 手机验证码登入
     *
     * @return
     */
    @PostMapping("/check")
    public Result check(@RequestBody Map<String, String> loginInfo, HttpServletResponse res) {
        //获取前端传过来的验证码
        String telephone = loginInfo.get("telephone");
        String validateCode = loginInfo.get("validateCode");
        //创建jedis
        Jedis jedis = jedisPool.getResource();
        //设置键
        String key = RedisMessageConstant.SENDTYPE_LOGIN + ":" + telephone;
        String longKey = jedis.get(key);
        //获取key的值，判断是否有验证码
        if (null == longKey) {
            //说明没有，提示
            return new Result(false, "请点击发送验证码");
        }
        //如果存在，则判断与用户输入输入的是否一致
        if (!longKey.equals(validateCode)) {
            return new Result(false, "验证码输入错误");
        }
        //清除验证码
        //jedis.del(key);
        //2.都正确了则判断是否为会员
        Member member = memberService.findByTelephone(telephone);
        if (null == member) {
            //说明不是会员，则执行添加
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            member.setRemark("手机快速注册");
            memberService.add(member);
        }
        //设置会员跟踪
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        //设置跟踪最长时间
        cookie.setMaxAge(5 * 24 * 60);
        //设置推送
        cookie.setPath("/");
        //存到网页中
        res.addCookie(cookie);
        //返回结果
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}