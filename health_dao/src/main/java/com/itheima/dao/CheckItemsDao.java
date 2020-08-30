package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @Author ${LiJian}
 * @Date 23:56 2020/8/20
 */
public interface CheckItemsDao {
    //查询所有信息
    List<CheckItem> findAll();

    /**
     * 用分页查询插件，进行的分页查询
     * @param queryString
     * @return
     */
    Page<CheckItem> findByConditem(String queryString);

    /**
     * 添加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    //查询是否有关联数据
    int findCountByCheckItemId(int id);

    /**
     *
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
