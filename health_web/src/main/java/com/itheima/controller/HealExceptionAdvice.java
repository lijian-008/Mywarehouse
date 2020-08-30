package com.itheima.controller;

import com.itheima.entity.Result;
import com.itheima.exception.HealthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Description: 全局异常处理
 * Advice: 通知
 * User: Eric
 * ExceptionHandler 获取的异常从小到大
 *
 */
@RestControllerAdvice
public class HealExceptionAdvice {

    /**
     *  info:  打印日志，记录流程性的内容
     *  debug: 记录一些重要的数据 id, orderId, userId
     *  error: 记录异常的堆栈信息，代替e.printStackTrace();
     *  工作中不能有System.out.println(), e.printStackTrace();
     */
    private static final Logger log = LoggerFactory.getLogger(HealthException.class);

    /**
     * ExceptionHandler 用来捕获取指定类型的异常
     * catch
     * @param e
     * @return
     */
    @ExceptionHandler(HealthException.class)
    public Result handleHealthException(HealthException e){
        return new Result(false, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        log.error("发生异常",e);
        return new Result(false, e.getMessage());
    }
}
