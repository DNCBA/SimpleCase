package com.mhc.codeGenerate;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.rss.RSS14Reader;
import com.google.zxing.qrcode.QRCodeWriter;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Hashtable;
import java.util.UUID;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-05-13 20:26
 */
public class CodeGenerate {


  private static String BASE_PATH = "/Users/leyan/Desktop/test/";

  /**
   * 二维码生成
   */
  @Test
  public void testGenerate() throws Exception {
    String data = UUID.randomUUID().toString();
    QRCodeWriter qrCodeWriter = new QRCodeWriter();

    //生成二维码
    Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
    hints.put(EncodeHintType.MARGIN, 1); //设置白边

    BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 240, 240, hints);
    MatrixToImageWriter
        .writeToFile(bitMatrix, "PNG", new File(BASE_PATH + "1.png"));
    System.out.println("success ");


  }

}
