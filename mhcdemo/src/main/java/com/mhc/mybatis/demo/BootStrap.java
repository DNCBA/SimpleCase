package com.mhc.mybatis.demo;

import com.mhc.mybatis.demo.config.MybatisConfig;
import com.mhc.mybatis.demo.mapper.AssistantMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-02-19 16:31
 */
public class BootStrap {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MybatisConfig.class);


        AssistantMapper assistantMapper = context.getBean(AssistantMapper.class);


        String result = assistantMapper.findById();


        System.out.println(result);

    }
}
