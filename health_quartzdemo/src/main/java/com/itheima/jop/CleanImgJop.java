package com.itheima.jop;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.SetMealService;
import com.itheima.utils.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author ${LiJian}
 * @Date 17:27 2020/8/25
 */
@Component("cleanImgJop")
public class CleanImgJop {
    @Reference
    private SetMealService setMealService;
    /**
     * 定时删除图片
     */
    public void cleanImg(){
        //获取七牛云上的图片
        List<String> file = QiNiuUtils.listFile();
        //获取数据库的图片
        List<String> list = setMealService.findImg();
        //用七牛云的图片数量减去数据库的图片数量
        file.removeAll(list);
        //剩下的就是要删除的垃圾图片
        String[] array = file.toArray(new String[]{});
        QiNiuUtils.removeFiles(array);
    }
}
