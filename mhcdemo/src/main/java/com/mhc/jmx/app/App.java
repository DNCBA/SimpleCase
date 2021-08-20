package com.mhc.jmx.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;

@Configuration
@EnableMBeanExport
@ComponentScan(basePackages = "com.mhc.jmx.app")
public class App {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(App.class);
        Service service = applicationContext.getBean(Service.class);
        service.run();

    }
}
