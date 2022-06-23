package com.mhc.spring.test.config;

import com.mhc.spring.test.MCondition;
import com.mhc.spring.test.enbale.EnableBean;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

@Service
@EnableBean
@Conditional(MCondition.class)
public class BeanApp {

    public String sayTTT() {
        System.out.println("BeanApp sayTTT");
        return "sayTTT";
    }
}
