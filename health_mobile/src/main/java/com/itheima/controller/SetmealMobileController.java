package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import com.itheima.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin2.message.Message;

import java.util.List;

/**
 * @Author ${LiJian}
 * @Date 18:05 2020/8/26
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {
    @Reference
    private SetMealService setMealService;

    @GetMapping("/getSetmeal")
    public Result findAll(){
        //查询所有套餐信息
        List<Setmeal> setmeals = setMealService.findA();
        //需要拼接图片返回
        setmeals.forEach(setmeal -> {
            setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        });
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeals);
    }

    /**
     * 通过套餐id，查询检查组与检查项的id
     * @param id
     * @return
     */
    @GetMapping("/findDetailById")
    public Result findDetailById(int id){
        //通过id，查询出套餐的相关信息
        Setmeal setmeal = setMealService.findDetailById(id);
        //拼接好图片路径，返回
        setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        //返回
        return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmeal);
    }

    /**
     * 通过id查询套餐信息
     * @param id
     * @return
     */
    @PostMapping("/findById")
    public Result findById(int id){
        //调用业务层的方法
        Setmeal setmeal = setMealService.findById(id);
        //设置拼接图片
        setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        //查询成功，返回页面
        return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmeal);
    }
}
