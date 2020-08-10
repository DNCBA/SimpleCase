package com.mhc.spring.extend.aware;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-07-13 19:37
 */
@Component
public class BeanNameAwaerImpl implements BeanNameAware {

  @Override
  public void setBeanName(String name) {
    System.out.println("BeanNameAwaerImpl ------> "+name);
  }
}
