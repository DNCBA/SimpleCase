package com.mhc.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PdfTest {

    public static void main(String[] args) throws Exception {

        //根据模板生成
        testTemplateToPDF();
        //根据html生成
        testHtmlToPDF();
    }

    private static void testHtmlToPDF() {

    }

    private static void testTemplateToPDF() throws IOException, DocumentException {

        //准备渲染数据
        HashMap<String, String> data = new HashMap<>();
        data.put("userName","风清扬");
        data.put("age","17");
        data.put("address","北京西单西二旗");
        //准备模板和输出
        URL resource = PdfTest.class.getClassLoader().getResource("pdf/template.pdf");
        File templateFile = new File(resource.getFile());
        File result = new File("test1.pdf");
        if (!result.exists()){
            result.createNewFile();
        }
        FileOutputStream outputStream = new FileOutputStream(result);
        //开始渲染
        PdfReader pdfReader = new PdfReader("pdf/template.pdf");
        PdfStamper pdfStamper = new PdfStamper(pdfReader, outputStream);
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
        fontList.add(baseFont);
        AcroFields fields = pdfStamper.getAcroFields();
        fields.setSubstitutionFonts(fontList);
        for (Map.Entry entry : data.entrySet()){
            fields.setField((String) entry.getKey(), (String) entry.getValue());
        }
        pdfStamper.setFormFlattening(true);
        pdfStamper.close();
        outputStream.flush();
        outputStream.close();
        System.out.println("根据pdf模板生成的文件,路径为:"+result.getAbsolutePath());
    }
}
