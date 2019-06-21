package com.mhc.poi;

import org.apache.kafka.common.protocol.types.Field.Str;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-06-21 10:58
 */
public class CreateWord {

  private static final String base_path = "/Users/leyan/Desktop/template.docx";
  private static final String out_path = "/Users/leyan/Desktop/demo.docx";


  @Test
  public void main() {
    try {

      //create();

      operation();

    } catch (Exception e) {
      e.printStackTrace();
    }


  }

  private void operation() throws IOException {
    XWPFDocument document = new XWPFDocument(new FileInputStream(new File(base_path)));

    List<XWPFParagraph> paragraphs = document.getParagraphs();

    List<XWPFTable> tables = document.getTables();

    paragraphs.forEach(tparagraphs->{
      System.out.println("graphs----> " +tparagraphs.getText());
    });

    tables.forEach(table->{
      List<XWPFTableRow> rows = table.getRows();
      for (XWPFTableRow row : rows){
        List<XWPFTableCell> tableCells = row.getTableCells();
        for (XWPFTableCell cell : tableCells){
          System.out.println("table---> " +cell.getText());
        }
      }
    });

    XWPFTableCell cell = tables.get(0).getRow(1).getCell(1);
    cell.setText("select * from student_task_score  where student_task_id=69032;\n"
        + "update student_task_score set total_score = 100, veracity_score = 100, fluency_score = 100, \n"
        + "comment = '瞧,你读得多好呀!朗读时，你不仅能做到读好音准，而且能把句子读通顺，把课文读流利。你的朗诵极富表现力，赞！', valid = 1 where student_task_id in(71181,71190,71156,71172)");

    System.out.println("table- ->" + tables.get(0).getText());

    document.write(new FileOutputStream(new File(out_path)));
  }

  private void create() throws IOException {
    //创建一个 word 文档
    XWPFDocument document = new XWPFDocument();

    //创建一个文本
    XWPFParagraph paragraph = document.createParagraph();
    //设置格式
    paragraph.setAlignment(ParagraphAlignment.CENTER);
    //设置文本和文本对应的格式
    XWPFRun run = paragraph.createRun();
    run.setText("Hello poi");
    //run.setColor("1111111");
    run.setFontSize(20);
    //设置背景相关参数
//      CTR ctr = run.getCTR();
////      CTShd ctShd = ctr.addNewRPr().addNewShd();
////      ctShd.setVal(STShd.CLEAR);
////      ctShd.setFill("00000");

    //创建一个table
    XWPFTable table = document.createTable();

    //设置样式
//      CTTblWidth infoTableWidth = table.getCTTbl().addNewTblPr().addNewTblW();
//      infoTableWidth.setType(STTblWidth.DXA);
//      infoTableWidth.setW(BigInteger.valueOf(9072));

    //创建一行
    XWPFTableRow row = table.getRow(0);
    row.setHeight(100);

    //创建一行中的一列
    row.getCell(0).setText("1-1");
    row.createCell().setText("1-2");
    row.createCell().setText("1-3");

    XWPFTableRow row1 = table.createRow();
    row1.getCell(0).setText("2-1");
//      row1.createCell().setText("2-1");

    document.write(new FileOutputStream(new File(base_path)));
  }


}
