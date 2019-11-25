package com.mhc.design_pattern.singleon;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-11-25 13:37
 * 饱汉模式
 * 缺点:不论是否使用都会实例化对象
 * 优点:写法简单,静态变量线程安全
 */
public class Singleton1 {

  //私有构造
  private Singleton1() {
  }

  //静态属性,在类加载的第一次进行加载,线程安全
  private static Singleton1 instance = new Singleton1();

  public static Singleton1 getInstance() {
    return instance;
  }


}
