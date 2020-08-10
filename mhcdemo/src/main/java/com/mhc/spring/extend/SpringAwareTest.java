package com.mhc.spring.extend;

import com.alibaba.fastjson.JSON;
import com.mhc.spring.extend.bean.BeanTest;
import com.mhc.spring.extend.config.SpringConfigeration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-07-13 19:33
 */
public class SpringAwareTest {

  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        SpringConfigeration.class);

    Object map = applicationContext.getBean("map");
    System.out.println(JSON.toJSONString(map));
    BeanTest bean = applicationContext.getBean(BeanTest.class);
    bean.sayHello("tome");

  }

}
