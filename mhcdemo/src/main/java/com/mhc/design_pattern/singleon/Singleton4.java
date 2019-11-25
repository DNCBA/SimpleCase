package com.mhc.design_pattern.singleon;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-11-25 13:42
 * 饿汉模式
 * 通过缩小锁的粒度来提高效率
 */
public class Singleton4 {

  private Singleton4() {
  }

  private static Singleton4 instance;


  public static Singleton4 getInstance() {
    if (null == instance) {
      synchronized (Singleton4.class) {
        if (null == instance) {
          instance = new Singleton4();
        }
      }
    }
    return instance;
  }
}
