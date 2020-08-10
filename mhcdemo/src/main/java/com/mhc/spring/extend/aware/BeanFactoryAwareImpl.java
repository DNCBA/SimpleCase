package com.mhc.spring.extend.aware;

import com.mhc.spring.extend.bean.BeanTest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-07-13 19:47
 */
@Component
public class BeanFactoryAwareImpl implements BeanFactoryAware {

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    System.out.println("BeanFactoryAwareImpl ------> "+beanFactory.getBean(BeanTest.class).getClass().toString());
  }
}
