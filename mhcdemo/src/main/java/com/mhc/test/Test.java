package com.mhc.test;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-06-22 16:47
 */
public class Test {

  public static void main(String[] args) {
    String s = "abc";
    String c = s+"def";
    String e = c+"ghd";
    System.out.println(e);
    StringBuilder sb = new StringBuilder("abc");
    sb.append("def");
    System.out.println(sb.toString());

  }

}










