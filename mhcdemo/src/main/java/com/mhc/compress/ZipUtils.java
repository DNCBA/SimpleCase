package com.mhc.compress;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019.txt-06-21 19:37
 */
public class ZipUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtils.class);

  private ZipUtils() {}


  public static void zipFiles(List<MutablePair<String, byte[]>> bytes)  throws IOException  {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
    for (MutablePair<String, byte[]> aByte : bytes) {
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(aByte.getRight());
      ZipEntry zipEntry = new ZipEntry(aByte.getLeft());
      zipOutputStream.putNextEntry(zipEntry);
      copy(zipOutputStream, byteArrayInputStream);
      byteArrayInputStream.close();
    }
    zipOutputStream.finish();
    File file = new File("test.zip");
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    IOUtils.copy(byteArrayInputStream, fileOutputStream);
    LOGGER.info("result path: {}", file.getAbsolutePath());
  }



  public static List<MutablePair<String, byte[]>> unZipFile()   throws IOException {
    File file = new File("test.zip");
    FileInputStream fileInputStream = new FileInputStream(file);
    ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);

    List<MutablePair<String, byte[]>> result = new ArrayList<>();
    ZipEntry entry = null;
    while (null != (entry = zipInputStream.getNextEntry())) {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      copy(byteArrayOutputStream, zipInputStream);
      byte[] bytes = byteArrayOutputStream.toByteArray();
      result.add(MutablePair.of(entry.getName(), bytes));
    }
    return result;

  }




  /**
   * @param resFiles 待压缩的文件列表
   * @param zipFilePath 压缩文件生成的目录
   */
  public static File zipFiles(List<File> resFiles, String zipFilePath) throws IOException {
    FileOutputStream fileOutputStream = null;
    ZipOutputStream zipOutputStream = null;
    File zipFile = null;
    try {
      //输出文件路径是否存在
      File zipPath = new File(zipFilePath);
      if (!zipPath.exists()) {
        zipPath.mkdirs();
      }
      //生成对应的压缩名
      zipFile = createZipFile(zipFilePath);
      //开始压缩
      zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
      for (File file : resFiles) {
        FileInputStream fileInputStream = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zipOutputStream.putNextEntry(zipEntry);
        copy(zipOutputStream, fileInputStream);
        fileInputStream.close();
      }
    }finally {
      if (null != zipOutputStream){
        zipOutputStream.close();
      }
      if (null != fileOutputStream){
        fileOutputStream.close();
      }
    }
    return zipFile;
  }

  /**
   * 流对考
   * @param zipOutputStream
   * @param fileInputStream
   * @throws IOException
   */
  private static void copy(OutputStream zipOutputStream, InputStream fileInputStream)
      throws IOException {
    Integer len = null;
    byte[] bytes = new byte[1024];
    while (0 <= (len = fileInputStream.read(bytes))) {
      zipOutputStream.write(bytes, 0, len);
    }
  }

  /**
   * 在指定目录下生成一个 zip 压缩文件，文件名称 日期 + uuid .zip
   * @param zipFilePath  zip 的基准目录
   * @return
   */
  private static File createZipFile(String zipFilePath) throws IOException {
    String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    String uuid = UUID.randomUUID().toString().replace("-", "").substring(0,5);
    String fileName = String.format("%s/%s-%s.zip",zipFilePath, date, uuid);
    File file = new File(fileName);
    if (!file.exists()){
      file.createNewFile();
    }
    return file;
  }


  public static void main(String[] args) {
    try {
      //模板文件地址
      String inputUrl = "/Users/leyan/Desktop/template.docx";
      //新生产的模板文件
      String outputUrl = "/Users/leyan/Desktop/demo.docx";

      String desc = "/Users/leyan/Desktop/zip";

      ArrayList<File> files = new ArrayList<>();
      files.add(new File(inputUrl));
      files.add(new File(outputUrl));

      ZipUtils.zipFiles(files, desc);

    }catch (Exception e){
      e.printStackTrace();
    }
  }

}
