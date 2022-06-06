package com.mhc.spring.test;

import com.mhc.spring.test.enbale.EnableBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBean
public class SpringTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringTest.class);
        IService service = context.getBean(IService.class);
        service.sayHello();
    }
}
