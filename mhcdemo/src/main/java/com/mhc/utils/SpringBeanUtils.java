package com.mhc.utils;

import org.apache.kafka.common.protocol.types.Field.Bool;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-07-19 21:40
 */
public class SpringBeanUtils {


  private static final Logger LOGGER = Logger.getLogger(SpringBeanUtils.class);
  private static ApplicationContext context;

  public static void loadContext(ApplicationContext applicationContext){
    SpringBeanUtils.context = applicationContext;
  }

  /**
   * 提供根据 alisName 获取对应的bean
   * 加载异常时返回 null
   */
  public static T getBean(Class<T> clazz){
    LOGGER.info(String.format("Bean Context : load bean %s",clazz.getName()));
    try {
      T bean = SpringBeanUtils.context.getBean(clazz);
      return bean;
    }catch (Exception e){
      LOGGER.error(String.format("Bean Context : load bean : %s ,error : %s",clazz.getName(),e.getMessage(),e));
    }
    return null;
  }



  /**
   * 根据提供的 class 获取对应的bean
   * 加载异常时返回 null
   */
  public static T getBean(String alisName){
    LOGGER.info(String.format("Bean Context : load bean %s",alisName));
    try {
      T bean = (T) SpringBeanUtils.context.getBean(alisName);
      return bean;
    }catch (Exception e){
      LOGGER.error(String.format("Bean Context : load bean : %s , error : %s",alisName,e.getMessage(),e));
    }
    return null;
  }



  /**
   * 提供自动注入 bean 的方法 ,默认采用类型首字母小写注入
   * 注入bean 为单例 bean
   */
  public static Boolean registerBean(Object bean){
    String simpleName = bean.getClass().getSimpleName();
    return registerBean(bean,simpleName);
  }



  /**
   * 提供注入 bean 的方法
   * 注入 bean 为单例 bean
   */
  public static Boolean registerBean(Object bean,String alisName){
    LOGGER.info(String.format("Bean Context : register bean %s",bean.getClass().getName()));
    try {

      DefaultListableBeanFactory register = (DefaultListableBeanFactory) SpringBeanUtils.context;
      register.registerSingleton(alisName,bean);
      return true;
    }catch (Exception e){
      LOGGER.error(String.format("Bean Context : register bean %s , error : %s",bean.getClass().getName(),e.getMessage()),e);
    }
    return false;
  }

  /**
   * 提供深克隆的方法
   * 异常时返回 null
   */
  public static T deepClone(T bean){
    LOGGER.info(String.format("Bean Context : deepClone bean %s",bean.getClass().getName()));
    try {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
      objectOutputStream.writeObject(bean);
      objectOutputStream.flush();
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
      ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
      return (T) objectInputStream.readObject();
    }catch (Exception e){
      LOGGER.info(String.format("Bean Context : deepClone bean %s , error : %s",bean.getClass().getName(),e.getMessage()),e);
    }
    return null;
  }






}
