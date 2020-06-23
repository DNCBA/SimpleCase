package com.mhc.test;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-06-23 10:38
 */
public class FinalTest {

  public String getResult() {
    String result = "abc";
    try {
      return result;
    }finally {
      result = "edf";
    }
  }
}













