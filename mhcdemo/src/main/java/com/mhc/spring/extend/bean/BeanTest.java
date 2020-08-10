package com.mhc.spring.extend.bean;

import org.springframework.stereotype.Component;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-07-13 19:42
 */
@Component
public class BeanTest {


  public void sayHello(String name){
    System.out.println("hello " + name);
  }

}
