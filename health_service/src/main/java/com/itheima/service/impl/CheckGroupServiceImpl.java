package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.HealthException;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import com.sun.xml.internal.ws.handler.HandlerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author ${LiJian}
 * @Date 12:57 2020/8/23
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService{
    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 查询分页
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findAll(QueryPageBean queryPageBean) {
        //使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //判断查询条件是否为空
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            //不为空，执行分页查询
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //通过条件，调用方法，查询
        Page<CheckGroup>page = checkGroupDao.findAll(queryPageBean.getQueryString());
        //查询到的结果分装到pageresult中返回
        PageResult<CheckGroup>list = new PageResult<>(page.getTotal(),page.getResult());
        return list;
    }

    /**
     * 查询所有检查项
     * @return
     */
    @Override
    public List<CheckItem> findCheckGroup() {
        //调用dao层
        List<CheckItem>list=checkGroupDao.findCheckGroup();
        return list;
    }

    /**
     * 新增检查项
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds)throws HealthException {
        //先新增检查项
        checkGroupDao.add(checkGroup);
        //获取新增检查项的id
        Integer checkGroupId = checkGroup.getId();
        //便利检查项的id
        if (null!=checkitemIds) {
            for (Integer checkitemId : checkitemIds) {
                //把id添加到关联表中
                checkGroupDao.addCheckItem(checkGroupId,checkitemId);
            }
        }
    }
    //删除检查组
    @Override
    @Transactional
    public void deleteById(int id)throws HealthException {
        //判断检查项是否被套餐使用
       int byId = checkGroupDao.findById(id);
       //判断是否有数据关联
        if (byId>0) {
            throw new HealthException("查询组被使用，不能删除");
        }
        //没有关联的删除
        checkGroupDao.deleteCheckgroup(id);
    }

    /**
     * 回显检查组
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findByCheckGroupId(id);
    }

    /**
     * 回显关联id
     * @param id
     * @return
     */
    @Override
    public List<Integer> findByCheckGroupId(int id) {
        return checkGroupDao.findCheckGroupId(id);
    }

    @Override
    public void updata(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组
        checkGroupDao.updata(checkGroup);
        //删除旧的值
        checkGroupDao.deleteCheck(checkGroup.getId());
        //遍历出所有选中的id
        if (null!=checkitemIds) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckItem(checkGroup.getId(),checkitemId);
            }
        }
    }
}
