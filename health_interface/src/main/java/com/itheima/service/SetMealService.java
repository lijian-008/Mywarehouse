package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.HealthException;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;

import java.util.List;

/**
 * @Author ${LiJian}
 * @Date 0:00 2020/8/24
 */
public interface SetMealService {
    /**
     * 查询分页
     * @param queryPageBean
     * @return
     */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    /**
     * 检查组回显
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 添加
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds)throws HealthException;

    /**
     * 删除
     * @param id
     */
    void deleteById(int id) throws HealthException;

    /**
     * 回显套餐
     * @param id
     * @return
     */
    Setmeal findById(int id);

    /**
     *回显关系表
     * @param id
     * @return
     */
    List<Integer> findCheckgroupId(int id);

    /**
     * 编辑套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void update(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 查询所有图片
     * @return
     */
    List<String> findImg();

    /**
     * 查询所有套餐
     * @return
     */
    List<Setmeal> findA();

    /**
     *
     * @param id
     * @return
     */
    Setmeal findDetailById(int id);
}
