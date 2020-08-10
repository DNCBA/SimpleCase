package com.mhc.spring.extend.aware;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.stereotype.Component;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-07-13 19:45
 */
@Component
public class BeanClassLoaderAwareImpl implements BeanClassLoaderAware {

  @Override
  public void setBeanClassLoader(ClassLoader classLoader) {
    System.out.println("BeanClassLoaderAwareImpl ------> "+classLoader.toString());
  }
}
