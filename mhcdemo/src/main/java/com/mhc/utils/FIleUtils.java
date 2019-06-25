package com.mhc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-06-25 21:19
 */
public class FIleUtils {

  private void FileUtils(){}

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
