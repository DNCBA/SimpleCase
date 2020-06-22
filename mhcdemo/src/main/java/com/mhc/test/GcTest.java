package com.mhc.test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-06-22 17:18
 */
public class GcTest {

  public static void main(String[] args)  {

    try {

    ArrayList<Object> objects = new ArrayList<>();
    int a = 0;
    while (true){
      byte[] bytes = new byte[102400];
      objects.add(bytes);
      TimeUnit.SECONDS.sleep(100);
      System.out.println(a++);
      if (a/76==1){
        objects.clear();
      }
    }

    }catch (Exception e){
      e.printStackTrace();
    }
  }

}
