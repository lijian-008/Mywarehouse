package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.HealthException;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;
import org.apache.zookeeper.data.Id;

import java.util.List;

/**
 * @Author ${LiJian}
 * @Date 0:01 2020/8/24
 */
public interface SetMealServiceDao {
    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<Setmeal> findPage(String queryString);

    /**
     * 回显检查组
     * @return
     */
    List<CheckGroup> findAll();

    /**
     * 添加套餐
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 添加关联表信息
     * @param setmealId
     * @param checkgroupId
     */
    void addGroup(@Param("setmealId") Integer setmealId, @Param("checkgroupId") Integer checkgroupId);

    /**
     * 查询是否有关联
     * @param id
     * @return
     */
    int findSetMealCheckGroup(int id);

    /**
     * 删除
     * @param id
     */
    void deleteById(int id);

    /**
     * 回显所有套餐数据
     * @param id
     * @return
     */
    Setmeal findById(int id);

    List<Integer> findCheckgroupId(int id);

    /**
     * 修改表单信息
     * @param setmeal
     */
    void update(Setmeal setmeal)throws HealthException;

    /**
     * 删除旧的
     * @param id
     */
    void deleteSetmeal(Integer id);

    /**
     * 查询所有图片
     * @return
     */
    List<String> findImg();

    /**
     * 查询所有套餐信息
     * @return
     */
    List<Setmeal> findA();

    /**
     * 通过套餐id，查询检查组与检查项的id
     * @return
     */
    Setmeal findDetailById(int id);

}
