package com.mhc.poi.easyExcel;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-10-21 13:18
 */


@HeadRowHeight(20)
@ColumnWidth(25)
public class UserInfo {

  @ExcelProperty(value = "index")
  private Long index;
  @ExcelProperty(value = "userName")
  private String userName;
  @ExcelProperty(value = "passWord")
  private String passWord;

  public UserInfo(Long index, String userName, String passWord) {
    this.index = index;
    this.userName = userName;
    this.passWord = passWord;
  }

  public UserInfo() {
  }

  public Long getIndex() {
    return index;
  }

  public void setIndex(Long index) {
    this.index = index;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassWord() {
    return passWord;
  }

  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }
}
