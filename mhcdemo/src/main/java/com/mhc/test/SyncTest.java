package com.mhc.test;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-06-23 10:56
 */
public class SyncTest {


  public static synchronized void testStaticSync(){
    System.out.println("sync1");
  }

  public synchronized void testSync(){
    System.out.println("sync2");
  }

  public void test(){
    synchronized (this){
      synchronized (this) {
        System.out.println("sync3");
      }
    }
  }
}





