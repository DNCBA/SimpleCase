package com.mhc.design_pattern.singleon;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-11-25 13:49
 * 饿汉模式
 * 缺点:方法加锁性能下降
 */
public class Singleton3 {

  private Singleton3() {
  }


  private static Singleton3 instance;


  public synchronized static Singleton3 getInstance() {
    if (null == instance) {
      instance = new Singleton3();
    }
    return instance;
  }

}
