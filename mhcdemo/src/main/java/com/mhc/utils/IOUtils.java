package com.mhc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-06-25 20:54
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

}
