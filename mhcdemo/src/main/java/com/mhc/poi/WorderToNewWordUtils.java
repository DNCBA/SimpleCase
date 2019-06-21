package com.mhc.poi;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * 通过word模板生成新的word工具类
 */
public class WorderToNewWordUtils {

  private static  XWPFDocument templateDocument = null;

  /**
   * 根据模板生成新word文档
   * 判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
   *
   */
  public static boolean changWord(String inputUrl, String outputUrl,
      Map<String, String> textMap) {

    //模板转换默认成功
    boolean changeFlag = true;
    try {
      //获取docx解析对象
      XWPFDocument document = WorderToNewWordUtils.loadtemplate(inputUrl);
      //解析替换文本段落对象
      WorderToNewWordUtils.changeText(document, textMap);
      //解析替换表格对象
      WorderToNewWordUtils.changeTable(document, textMap);

      //生成新的word
      File file = new File(outputUrl);
      FileOutputStream stream = new FileOutputStream(file);
      document.write(stream);
      stream.close();

    } catch (IOException e) {
      e.printStackTrace();
      changeFlag = false;
    }

    return changeFlag;

  }

  /**
   *  加载模版
   * @param inputUrl 模版所在的路径
   * @return
   */
  private static XWPFDocument loadtemplate(String inputUrl) throws IOException {
    //防止频繁加载模版文件导致磁盘负载过大
    if (null == templateDocument){
      templateDocument = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));
    }
    //创建对应的doc
    return templateDocument;
  }

  /**
   * 替换段落文本
   * @param document docx解析对象
   * @param textMap 需要替换的信息集合
   */
  public static void changeText(XWPFDocument document, Map<String, String> textMap){
    //获取段落集合
    List<XWPFParagraph> paragraphs = document.getParagraphs();

    for (XWPFParagraph paragraph : paragraphs) {
      //判断此段落时候需要进行替换
      String text = paragraph.getText();
      if(checkText(text)){
        List<XWPFRun> runs = paragraph.getRuns();
        for (XWPFRun run : runs) {
          //替换模板原来位置
          run.setText(changeValue(run.toString(), textMap),0);
        }
      }
    }

  }

  /**
   * 替换表格对象方法
   * @param document docx解析对象
   * @param textMap 需要替换的信息集合
   */
  public static void changeTable(XWPFDocument document, Map<String, String> textMap){
    //获取表格对象集合
    List<XWPFTable> tables = document.getTables();
    for (int i = 0; i < tables.size(); i++) {
      //只处理行数大于等于2的表格，且不循环表头
      XWPFTable table = tables.get(i);
      if(table.getRows().size()>1){
        //判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
        if(checkText(table.getText())){
          List<XWPFTableRow> rows = table.getRows();
          //遍历表格,并替换模板
          eachTable(rows, textMap);
        }
      }
    }
  }





  /**
   * 遍历表格
   * @param rows 表格行对象
   * @param textMap 需要替换的信息集合
   */
  public static void eachTable(List<XWPFTableRow> rows ,Map<String, String> textMap){
    for (XWPFTableRow row : rows) {
      List<XWPFTableCell> cells = row.getTableCells();
      for (XWPFTableCell cell : cells) {
        //判断单元格是否需要替换
        if(checkText(cell.getText())){
          List<XWPFParagraph> paragraphs = cell.getParagraphs();
          for (XWPFParagraph paragraph : paragraphs) {
            List<XWPFRun> runs = paragraph.getRuns();
            for (XWPFRun run : runs) {
              run.setText(changeValue(run.toString(), textMap),0);
            }
          }
        }
      }
    }
  }

  /**
   * 为表格插入数据，行数不够添加新行
   * @param table 需要插入数据的表格
   * @param tableList 插入数据集合
   */
  public static void insertTable(XWPFTable table, List<String[]> tableList){
    //创建行,根据需要插入的数据添加新行，不处理表头
    for(int i = 1; i < tableList.size(); i++){
      XWPFTableRow row =table.createRow();
    }
    //遍历表格插入数据
    List<XWPFTableRow> rows = table.getRows();
    for(int i = 1; i < rows.size(); i++){
      XWPFTableRow newRow = table.getRow(i);
      List<XWPFTableCell> cells = newRow.getTableCells();
      for(int j = 0; j < cells.size(); j++){
        XWPFTableCell cell = cells.get(j);
        cell.setText(tableList.get(i-1)[j]);
      }
    }

  }



  /**
   * 判断文本中时候包含$
   * @param text 文本
   * @return 包含返回true,不包含返回false
   */
  public static boolean checkText(String text){
    boolean check  =  false;
    if(text.indexOf("$")!= -1){
      check = true;
    }
    return check;

  }

  /**
   * 匹配传入信息集合与模板
   * @param value 模板需要替换的区域
   * @param textMap 传入信息集合
   * @return 模板需要替换区域信息集合对应值
   */
  public static String changeValue(String value, Map<String, String> textMap){
    Set<Entry<String, String>> textSets = textMap.entrySet();
    for (Entry<String, String> textSet : textSets) {
      //匹配模板与替换值 格式${key}
      String key = "${"+textSet.getKey()+"}";
      if(value.indexOf(key)!= -1){
        value = textSet.getValue();
      }
    }
    return value;
  }




  public static void main(String[] args) throws Exception {
    //模板文件地址
    String inputUrl = "/Users/leyan/Desktop/template.docx";
    //新生产的模板文件
    String outputUrl = "/Users/leyan/Desktop/demo.docx";

//    Map<String, String> testMap = new HashMap<String, String>();
//    testMap.put("name", "小明");
//    testMap.put("sex", "男");
//    testMap.put("address", "软件园");
//    testMap.put("phone", "88888888");
//    testMap.put("year", "2000");
//
//    WorderToNewWordUtils.changWord(inputUrl, outputUrl, testMap);

    ByteOutputStream byteOutputStream = new ByteOutputStream();
    FileUtils.copyFile(new File(inputUrl),byteOutputStream);
    byte[] bytes = byteOutputStream.getBytes();
    XWPFDocument document = new XWPFDocument(new ByteInputStream(bytes, bytes.length));
    List<XWPFParagraph> paragraphs = document.getParagraphs();

  }
}