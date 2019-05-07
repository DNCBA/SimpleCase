package com.mhc.poi;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;

public class CreateExcel {


    @Test
    public void createExcel(){
        try {
            // 创建工作薄
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 创建工作表
            HSSFSheet sheet = workbook.createSheet("sheet1");

            for (int row = 0; row < 10; row++) {
                HSSFRow rows = sheet.createRow(row);
                for (int col = 0; col < 10; col++) {
                    // 向工作表中添加数据
                    rows.createCell(col).setCellValue("data" + row + col);
                }
            }
            File xlsFile = new File("/Users/leyan/Desktop/poi.xls");
            FileOutputStream xlsStream = new FileOutputStream(xlsFile);
            workbook.write(xlsStream);


        }catch (Exception e){
            e.printStackTrace();
        }


    }



}
