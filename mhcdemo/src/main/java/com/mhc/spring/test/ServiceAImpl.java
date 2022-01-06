package com.mhc.spring.test;


import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ServiceAImpl implements IService {

    @PostConstruct
    public void init() {
        System.out.println("init:" + this.getClass().getSimpleName());
    }


    @Override
    public String sayHello() {
        System.out.println("serviceA sayHello");
        return "hello";
    }
}
