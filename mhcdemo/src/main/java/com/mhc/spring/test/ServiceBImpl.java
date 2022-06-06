package com.mhc.spring.test;


import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ServiceBImpl extends ServiceAImpl {

    @Override
    public String sayHello() {
        System.out.println("serviceB sayHello");
        return "serviceB";
    }
}
