package com.mhc.design_pattern.singleon;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-11-25 13:56 静态内部类方法 静态内部类的静态变量在类加载的时候不会被加载
 */
public class Singleton5 {

  private Singleton5() {
  }

  private static class InnerClass {

    public static Singleton5 INSTANCE = new Singleton5();
  }

  public static Singleton5 getInstance() {
    return InnerClass.INSTANCE;
  }

}
