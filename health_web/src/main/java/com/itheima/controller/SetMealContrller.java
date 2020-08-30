package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.GET;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author ${LiJian}
 * @Date 23:58 2020/8/23
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealContrller {
    @Reference
    private SetMealService setMealService;
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //分页查询
       PageResult<Setmeal> pageResult= setMealService.findPage(queryPageBean);
       //查询成功
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);
    }

    /**
     * 图片上传
     * @param imgFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile){
        //获取图片原有名称
        String filename = imgFile.getOriginalFilename();
        //截取文件名后缀
        String substring = filename.substring(filename.lastIndexOf("."));
        //生成唯一文件名，拼接后缀
        String img = UUID.randomUUID() + substring;
        try {
            //上传到七牛云
            QiNiuUtils.uploadViaByte(imgFile.getBytes(),img);
            //上传成功，返回给页面数据

            Map<String,String> map = new HashMap();
            map.put("imgName",img);//文件名
            map.put("domain",QiNiuUtils.DOMAIN);

            //上传成功，返回页面
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(true,MessageConstant.PIC_UPLOAD_FAIL);
    }

    /**
     * 回显检查组
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(){
        //调用业务层方法
        List<CheckGroup>list =setMealService.findAll();
        //返回页面
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_FAILL,list);
    }

    /**
     * 添加套餐
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        setMealService.add(setmeal,checkgroupIds);
        //添加成功
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        //调用业务成方法
        setMealService.deleteById(id);
        //返回数据
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESSS);
    }

    /**
     * 回显
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        //调用业务层，查询所有套餐数据
        Setmeal list = setMealService.findById(id);

        Map<String, Object>map = new HashMap<>();
        map.put("domain",QiNiuUtils.DOMAIN);
        map.put("setmeal",list);
        //查询所有
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,map);
    }

    /**
     * 回显检查组信息
     * @return
     */
    @GetMapping("/findCheckgroupId")
    public Result findCheckgroupId(int id){
        List<Integer> checkGroup= setMealService.findCheckgroupId(id);
        System.out.println(checkGroup);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_FAILL,checkGroup);
    }

    /**
     * 执行修改
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        //调用业务层的方法
        setMealService.update(setmeal,checkgroupIds);
        //修改成功
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESSSe);
    }
}
