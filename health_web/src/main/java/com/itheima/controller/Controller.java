package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author ${LiJian}
 * @Date 20:51 2020/8/20
 */
@RestController
@RequestMapping("/check")
public class Controller {
    @Reference
    private CheckItemService checkItemService;
    @GetMapping("/findAll")
    public Result findAll(){
        try {
            //调用业务层查询所有信息
            List<CheckItem> list = checkItemService.findAll();
            //查询到的信息，返回页面展示
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用业务层的方法，查询总条数，和每页条数的集合
        PageResult<CheckItem> list =checkItemService.findPage(queryPageBean);
        //查询成功，返回数据
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }

    /**
     * 添加检查项
     * @param checkItem
     * @return
     */
    @RequestMapping("/add")
    public Result addCheckItem(@RequestBody CheckItem checkItem){
        //调用业务层的方法，执行添加
        checkItemService.addCheckItem(checkItem);
        //添加成功，返回页面
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 删除信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteById")
    public Result deleteById(int id){
        //调用业务层的方法，删除信息
        checkItemService.deleteById(id);
        //删除成功
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 回显数据
     * @param id
     * @return
     */
    @RequestMapping("/findId")
    public Result findId(int id){
        //调用业务层查询一条数据
        CheckItem checkItem = checkItemService.findId(id);
        //回显成功
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    /**
     * 修改数据
     * @return
     */
    @RequestMapping("/update")
    public Result updateCheck(@RequestBody CheckItem checkItem){
        //调用业务层
        checkItemService.update(checkItem);
        //修改成功
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
}
