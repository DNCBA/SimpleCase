package com.mhc.utils;

import java.io.*;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019.txt-06-25 20:54
 */
public class IOUtils {

  private static final Integer EOF = -1;
  private static final Integer DEFAULT_BUFFER_SIZE = 1024*4;

  private void IOUtils(){};


  /**
   *  流对考
   * @param inputStream
   * @param outputStream
   * @return
   */
  public static int copy(InputStream inputStream , OutputStream outputStream) throws IOException {
    byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
    int i = 0;
    int count = 0;
    if (EOF != (i = inputStream.read(bytes))){
      outputStream.write(bytes);
      outputStream.flush();
      count += i;
    }
    return count;
  }

  /**
   * 流对考
   * @param reader
   * @param writer
   * @return
   * @throws IOException
   */
  public static int copy(Reader reader , Writer writer ) throws IOException {
    char[] chars = new char[DEFAULT_BUFFER_SIZE];
    int i;
    int count = 0;
    if (EOF != (i = reader.read(chars))){
      writer.write(chars);
      writer.flush();
      count += i;
    }
    return count;
  }


  /**
   * 拷贝文件
   * @param src
   * @param desc
   * @return
   * @throws IOException
   */
  public static int copyFile(File src ,File desc) throws IOException {
    FileInputStream inputStream = new FileInputStream(src);
    FileOutputStream outputStream = new FileOutputStream(desc);
    return IOUtils.copy(inputStream,outputStream);
  }

}
