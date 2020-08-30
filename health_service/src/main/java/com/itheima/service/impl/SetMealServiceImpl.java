package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.dao.SetMealServiceDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.HealthException;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ${LiJian}
 * @Date 0:00 2020/8/24
 */
@Service(interfaceClass = SetMealService.class)
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealServiceDao setMealServiceDao;

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        //使用插件工具类
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //判断查询条件是否为空
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            //进行条件查询
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //查询到的总条数，每页条数分装到page集合中
        Page<Setmeal>page=setMealServiceDao.findPage(queryPageBean.getQueryString());
        //返回
        PageResult<Setmeal> setmealPageResult = new PageResult<>(page.getTotal(),page.getResult());
        return setmealPageResult;
    }

    /**
     * 回显检查组
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        //调用dao层的方法
        return setMealServiceDao.findAll();
    }

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) throws HealthException {
        //先添加套餐
        setMealServiceDao.add(setmeal);
        //通过添加的id，添加关联项
        Integer setmealId = setmeal.getId();
        //判断
        if (checkgroupIds!=null) {
            for (Integer checkgroupId : checkgroupIds) {
                //便利勾选出的id，添加到关联表
                setMealServiceDao.addGroup(setmealId,checkgroupId);
            }
        }
    }

    /**
     * 删除套餐
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) throws HealthException {
        //判断是否有套餐关联的信息
        int count=setMealServiceDao.findSetMealCheckGroup(id);
        //如果有，则不能删除
        if (count>0) {
            throw new HealthException("套餐被使用，不能删除");
        }else {
            //如果没有关联，执行删除
            setMealServiceDao.deleteById(id);
        }
    }

    /**
     * 回显所有套餐属性
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(int id) {
        return setMealServiceDao.findById(id);
    }

    /**
     * 关联的id
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckgroupId(int id) {
        return setMealServiceDao.findCheckgroupId(id);
    }

    /**
     * 编辑
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds)throws HealthException {
        //先修改表单
        setMealServiceDao.update(setmeal);
        //删除旧的套餐信息
        setMealServiceDao.deleteSetmeal(setmeal.getId());
        //添加新的套餐信息
        if (checkgroupIds!=null) {
            for (Integer checkgroupId : checkgroupIds) {
                setMealServiceDao.addGroup(setmeal.getId(),checkgroupId);
            }
        }
    }

    /**
     * 回显图片
     * @return
     */
    @Override
    public List<String> findImg() {
        return setMealServiceDao.findImg();
    }

    /**
     * 查询所有套餐信息
     * @return
     */
    @Override
    public List<Setmeal> findA() {
        return setMealServiceDao.findA();
    }

    /**
     * 通过套餐id，查询检查组与检查项的id
     * @param id
     * @return
     */
    @Override
    public Setmeal findDetailById(int id) {
        return setMealServiceDao.findDetailById(id);
    }
}
