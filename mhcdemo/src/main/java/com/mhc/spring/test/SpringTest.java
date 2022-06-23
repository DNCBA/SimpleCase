package com.mhc.spring.test;

import com.mhc.spring.test.config.BeanApp;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.mhc.spring.test.config")
public class SpringTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringTest.class);

        IService iService = context.getBean(IService.class);
        iService.sayHello();


        BeanApp beanApp = context.getBean(BeanApp.class);
        beanApp.sayTTT();



    }
}
