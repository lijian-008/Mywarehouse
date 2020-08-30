package com.itheima.jop;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Author ${LiJian}
 * @Date 13:16 2020/8/25
 */

public class JopAppliction {
    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:spring-jop.xml");
        System.in.read();
    }
}
