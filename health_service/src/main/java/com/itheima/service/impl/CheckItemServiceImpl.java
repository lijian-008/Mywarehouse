package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.dao.CheckItemsDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author ${LiJian}
 * @Date 23:39 2020/8/20
 *
 * Description: No Description
 * User: Eric  jdk proxy com.sum.proxy
 * interfaceClass发布到zookeeper上的接口
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemsDao checkItemsDao;
    @Override
    public List<CheckItem> findAll() {
        //调用dao层的方法，查询
        return checkItemsDao.findAll();
    }

    /**
     * 查询分页
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //mapper接口方式的调用，里面传的参数就是，当前页，与每页条数
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //再进行模糊查询
        //判断条件是否存在
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            //有条件,则拼接
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //通过条件，可以查询出分页结果
        Page<CheckItem> page=checkItemsDao.findByConditem(queryPageBean.getQueryString());
        //把查询到的数据，存到pageresult返回
        //page.getTotal()总条数，每页条数集合

        PageResult<CheckItem> result = new PageResult<>(page.getTotal(),page.getResult());
        return result;
    }

    /**
     * 新增检查项
     * @param checkItem
     */
    @Override
    public void addCheckItem(CheckItem checkItem) {
        //调用dao层方法
        checkItemsDao.add(checkItem);
    }

    /**
     * 删除检查项
     * @param id
     */
    @Override
    public void deleteById(int id) {
        //调用dao层的方法，通过id查询是否与检查项有关联
        int cun =checkItemsDao.findCountByCheckItemId(id);
        if (cun>0) {
            throw new RuntimeException("有关联的数据，不能删除");
        }
        //如果没有关联就执行删除
        checkItemsDao.deleteById(id);
    }

    /**
     * 回显数据
     * @param id
     * @return
     */
    @Override
    public CheckItem findId(int id) {
        //调用dao方法
        CheckItem checkItem=checkItemsDao.findId(id);
        return checkItem;
    }

    /**
     * 修改数据
     * @param checkItem
     */
    @Override
    public void update(CheckItem checkItem) {
        checkItemsDao.update(checkItem);
    }
}
