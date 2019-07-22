package com.mhc.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-07-19 21:40
 */
public class ApplicationContextProvider implements ApplicationContextAware {

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringBeanUtils.loadContext(applicationContext);
  }
}
