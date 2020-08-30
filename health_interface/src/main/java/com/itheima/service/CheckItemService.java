package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @Author ${LiJian}
 * @Date 23:37 2020/8/20
 */
public interface CheckItemService {
    //查询所有信息
    List<CheckItem> findAll();

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    /**
     * 添加检查项
     * @param checkItem
     */
    void addCheckItem(CheckItem checkItem);

    /**
     * 删除检查项
     * @param id
     */
    void deleteById(int id);

    /**
     * 回显数据
     * @param id
     * @return
     */
    CheckItem findId(int id);

    /**
     * 修改数据
     * @param checkItem
     */
    void update(CheckItem checkItem);
}
