package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author ${LiJian}
 * @Date 19:26 2020/8/25
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile){
        //解析前端文件
        try {
            List<String[]> strings = POIUtils.readExcel(excelFile);
            //把解析完的数据，转成ordersetting
            List<OrderSetting>orderSettinglist = new ArrayList<>();
            //创建日期解析
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);

            Date date=null;
            OrderSetting setting=null;
            //遍历前端解析的文件,转换格式
            for (String[] dataArr : strings) {
                date = sdf.parse(dataArr[0]);

                Integer integer = Integer.valueOf(dataArr[1]);
                //创建ordersetting对象，存入遍历出来的信息
                setting = new OrderSetting(date,integer);
                //再添加到集合
                orderSettinglist.add(setting);
            }
            //调用业务层方法，执行添加
            orderSettingService.add(orderSettinglist);
            //添加成功
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
    }
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month){//参数是这种格式2020-8
        try {
            //调用业务层方法
            List<Map> list =orderSettingService.getOrderSettingByMonth(month);
            //获取预约数成功
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
        }
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
    }

    /**
     * 预约设置
     * @param orderSetting
     * @return
     */
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        //调用业务层的方法
        orderSettingService.editNumberByDate(orderSetting);
        //返回页面
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
    }
}
