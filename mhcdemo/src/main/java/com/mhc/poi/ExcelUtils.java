package com.mhc.poi;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-06-21 21:42
 */
public class ExcelUtils {


  private static HSSFWorkbook template = null;

  public ExcelUtils() {
  }

  public static Boolean changeExcel(String inputUrl, String outputUrl,
      Map<String, String> textMap) throws IOException {

    //获取模版
    HSSFWorkbook workbook = ExcelUtils.loadTemplate(inputUrl);

    //替换数据
    changText(workbook, textMap);

    //输出文件
    writeResult(workbook, outputUrl);

    return null;
  }

  /**
   * 输出结果文件
   */
  private static void writeResult(HSSFWorkbook workbook, String outputUrl) throws IOException {
    workbook.write(new File(outputUrl));
  }

  /**
   * 替换模版文件的值
   */
  private static void changText(HSSFWorkbook workbook, Map<String, String> textMap) {
    //遍历 excel 中的所有单元格数据
    HSSFSheet sheet = workbook.getSheetAt(0);
    //获取所有的行数据
    for (int i = 0; i < sheet.getLastRowNum(); i++) {
      HSSFRow row = sheet.getRow(i);
      for (int j = 0; j < row.getLastCellNum(); j++) {
        HSSFCell cell = row.getCell(j);
        replateText( workbook,cell,textMap);
      }
    }
  }

  /**
   *  设置样式
   * @param cell
   */
  private static void setStyle(HSSFWorkbook workbook,HSSFCell cell) {
    HSSFCellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setWrapText(true);  //设置自动换行
    cell.setCellStyle(cellStyle);
  }

  /**
   * 更换单元格中的数据
   * @param cell
   * @param textMap
   */
  private static void replateText(HSSFWorkbook workbook,HSSFCell cell, Map<String, String> textMap) {
    String cellValue = cell.getStringCellValue();
    if (checkText(cellValue)){
      String result = changeValue(cellValue, textMap);
      if (result.matches("^[0-9]\\d*$")){
        cell.setCellValue(Long.valueOf(result));
      }else {
        cell.setCellValue(result);
      }
      //设置样式
      if (result.toCharArray().length > 120) {
        setStyle(workbook, cell);
      }
    }
  }

  /**
   * 替换文本中的数据
   * @param celleValue 原始文本数据
   * @param textMap 需要替换的数据
   * @return
   */
  public static String changeValue(String celleValue,Map<String,String> textMap){
    for (Map.Entry<String,String> entry : textMap.entrySet()){
      String key = String.format("#%s",entry.getKey());
      celleValue = celleValue.replaceAll(key,entry.getValue());
    }
    return celleValue;
  }

  /**
   * 判断文本中时候包含$
   * @param text 文本
   * @return 包含返回true,不包含返回false
   */
  public static boolean checkText(String text){
    boolean check  =  false;
    if(text.indexOf("#")!= -1){
      check = true;
    }
    return check;
  }

  /**
   * 加载模版文件
   */
  private static HSSFWorkbook loadTemplate(String inputUrl) throws IOException {
    //防止频繁加载模版文件导致磁盘负载过大
    if (null == template) {
      template = new HSSFWorkbook(new FileInputStream(new File(inputUrl)));
    }
    //创建对应的doc
    return template;
  }


  public static void main(String[] args) throws IOException {
    //模板文件地址
    String inputUrl = "/Users/leyan/Desktop/template.xls";
    //新生产的模板文件
    String outputUrl = "/Users/leyan/Desktop/demo.xls";

    HashMap<String, String> map = new HashMap<>();
    map.put("year","2019");
    map.put("name","zslswmz");
    map.put("p1","110");
    map.put("p2","119");
    map.put("info","select * from student_task_score  where student_task_id=69032;\n"
        + "update student_task_score set total_score = 100, veracity_score = 100, fluency_score = 100, \n"
        + "comment = '瞧,你读得多好呀!朗读时，你不仅能做到读好音准，而且能把句子读通顺，把课文读流利。你的朗诵极富表现力，赞！', valid = 1 where student_task_id in(71181,71190,71156,71172)");

    ExcelUtils.changeExcel(inputUrl,outputUrl,map);

  }

}
