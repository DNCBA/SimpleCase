package com.mhc.mybatis.demo;

import com.mhc.mybatis.demo.config.MybatisConfig;
import com.mhc.mybatis.demo.mapper.AssistantMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class BootStrap {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MybatisConfig.class);


        AssistantMapper assistantMapper = context.getBean(AssistantMapper.class);


        String result = assistantMapper.findById("1");


        System.out.println(result);

    }
}
