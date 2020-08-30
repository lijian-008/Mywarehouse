package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.HealthException;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @Author ${LiJian}
 * @Date 12:57 2020/8/23
 */
public interface CheckGroupService {
    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckGroup> findAll(QueryPageBean queryPageBean);

    /**
     * 查询检查项
     * @return
     */
    List<CheckItem> findCheckGroup();

    /**
     * 新增检查项
     * @param checkGroup
     * @param checkitemIds
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds)throws HealthException;

    /**
     * 删除检查组
     * @param id
     */
    void deleteById(int id)throws HealthException;

    /**
     * 回显检查组
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 回显检查项勾选信息
     * @param id
     * @return
     */
    List<Integer> findByCheckGroupId(int id);

    /**
     * 执行修改信息
     * @param checkGroup
     * @param checkitemIds
     */
    void updata(CheckGroup checkGroup, Integer[] checkitemIds);
}
