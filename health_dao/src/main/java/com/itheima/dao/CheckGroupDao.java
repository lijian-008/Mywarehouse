package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.exception.HealthException;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ${LiJian}
 * @Date 13:00 2020/8/23
 */
public interface CheckGroupDao {

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<CheckGroup> findAll(String queryString);

    /**
     * 查询检查项
     * @return
     */
    List<CheckItem> findCheckGroup();

    /**
     * 添加到检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加到检查组与检查项的关联表
     * @param checkGroupId
     * @param checkitemId
     */
    void addCheckItem(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemId);

    /**
     * 查看检查组是否被套餐使用
     * @param id
     * @return
     */
    int findById(int id);

    /**
     * 通过id删除
     * @param id
     */
    void deleteCheckgroup(int id)throws HealthException;

    /**
     *回显检查组
     * @param id
     * @return
     */
    CheckGroup findByCheckGroupId(int id);

    /**
     * 回显
     * @param id
     * @return
     */
    List<Integer> findCheckGroupId(int id);

    /**
     * 修改检查组
     * @param checkGroup
     */
    void updata(CheckGroup checkGroup);

    /**
     * 删除旧信息
     * @param id
     */
    void deleteCheck(Integer id);
}
