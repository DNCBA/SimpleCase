package com.mhc.poi.easyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-10-21 12:02
 *
 * easyExcel的使用样例
 */
public class EasyExcelTest {


  private static Logger LOGGER = LoggerFactory.getLogger(EasyExcelTest.class);

  private static String EXCEL_PATH = "/Users/leyan/Desktop/userInfo_1.xlsx";
  private static String OUT_EXCEL_PATH = "/Users/leyan/Desktop/userInfo_2.xlsx";

  public static void main(String[] args) {
    //读取数据样例
    readExcelInfo();



    //写出数据样例
    writeExcelInfo();


  }

  /**
   * 将对应的数据写出到excel
   */
  private static void writeExcelInfo() {

    ArrayList<UserInfo> userInfos = new ArrayList<>();
    userInfos.add(new UserInfo(1L,"a","a"));
    userInfos.add(new UserInfo(2L,"b","b"));
    userInfos.add(new UserInfo(3L,"c","c"));

    EasyExcel.write(new File(OUT_EXCEL_PATH),UserInfo.class).sheet("aaa").doWrite(userInfos);

  }

  /**
   * 从excel读取对应的数据
   */
  private static void readExcelInfo() {

    UserInfoListener userInfoListener = new UserInfoListener();
    EasyExcel.read(new File(EXCEL_PATH),UserInfo.class,userInfoListener).sheet().doRead();

    List<UserInfo> context = userInfoListener.getContext();

    LOGGER.info(JSON.toJSONString(context));

  }


}
