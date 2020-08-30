package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author ${LiJian}
 * @Date 12:53 2020/8/23
 */
@RestController
@RequestMapping("/checkgroup")
public class GroupController {
    //注入服务接口
    @Reference
    private CheckGroupService checkGroupService;

    @PostMapping("/findGroup")
    public Result findAll(@RequestBody QueryPageBean queryPageBean){
        //调用业务层的方法
        PageResult<CheckGroup> list =checkGroupService.findAll(queryPageBean);
        //查询成功，返回数据
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }

    /**
     * 回显添加检查组
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(){
        //调用业务层，查询检查项列表
        List<CheckItem> list =checkGroupService.findCheckGroup();
        //查询成功，返回页面
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PostMapping("/add")
    public Result addAll(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        //调用业务层的方法，执行添加
        checkGroupService.add(checkGroup,checkitemIds);
        //添加成功
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 删除信息
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        //调用业务层方法
        checkGroupService.deleteById(id);
        //删除成功
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    /**
     * 回显检查组信息
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
       CheckGroup checkGroup = checkGroupService.findById(id);
       //回显成功
        return new Result(true,"检查项回显成功",checkGroup);
    }

    /**
     * 回显检查项勾选的信息
     * @param id
     * @return
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int id){
        List<Integer>list = checkGroupService.findByCheckGroupId(id);
        //回显成功
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,list);
    }

    /**
     * 修改检查项
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PostMapping("/updata")
    public Result update(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        //调用业务层的方法，执行添加
        checkGroupService.updata(checkGroup,checkitemIds);
        //添加成功
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }
}
