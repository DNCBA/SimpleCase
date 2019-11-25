package com.mhc.design_pattern.singleon;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-11-25 13:40
 * 饱汉模式
 * 静态代码块模式
 */
public class Singleton2 {

  private Singleton2() {
  }


  private static volatile Singleton2 instance;

  static {
    instance = new Singleton2();
  }

  public static Singleton2 getInstance() {
    return instance;
  }


}
