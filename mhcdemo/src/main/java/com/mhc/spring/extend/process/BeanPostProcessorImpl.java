package com.mhc.spring.extend.process;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-07-13 19:56
 */
@Component
public class BeanPostProcessorImpl implements BeanPostProcessor {

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    if (bean instanceof Map){
      Map m = (Map) bean;
      m.put("postProcessBeforeInitialization",2);
      System.out.println("postProcessBeforeInitialization -------> " + beanName);
    }
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof List){
      List l = (List) bean;
      l.add("postProcessAfterInitialization");
      System.out.println("postProcessAfterInitialization -------> " + beanName);
    }
    return bean;
  }
}
