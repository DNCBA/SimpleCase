package com.mhc.io.stream;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-03-16 11:34
 */
public class StreamTest {


  private static final String FILE_PATH = "/Users/leyan/Desktop/test.back";

  public static void main(String[] args) {
    try {
//      testFileOutputStream();
//      testFileInputStream();
//      testFileReader();
//      testBufferdReader();
//      testInputStreamReader();
    } catch (Exception e) {
      e.printStackTrace();
    }


  }

  private static void testInputStreamReader() throws IOException {
    InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(FILE_PATH));
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    String line = null;
    while (null != (line=bufferedReader.readLine())){
      System.out.println(line);
    }
  }

  private static void testBufferdReader() throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH));
    String line = null;
    while (null != (line = bufferedReader.readLine())){
      System.out.println(line);
    }
  }

  private static void testFileReader() throws IOException {
    FileReader fileReader = new FileReader(FILE_PATH);
    char[] chars = new char[1024];
    while (-1 != fileReader.read(chars)) {
      System.out.println(new String(chars));
    }
  }

  /**
   * 测试outputStream
   */
  private static void testFileOutputStream() throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH);
    fileOutputStream.write("hello file outputStream ".getBytes());
    fileOutputStream.flush();
    fileOutputStream.close();
    System.out.println("test outputStream success");
  }

  /**
   * 测试inputStream
   */
  private static void testFileInputStream() throws IOException {
    //输入流
    FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
    byte[] bytes = new byte[1024];
    while (-1 != fileInputStream.read(bytes)) {
      String s = new String(bytes);
      System.out.println(s);
    }
    fileInputStream.close();
  }


}
