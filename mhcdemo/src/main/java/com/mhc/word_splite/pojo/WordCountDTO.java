package com.mhc.word_splite.pojo;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-05-22 14:03
 */
public class WordCountDTO {

  private String text;
  private Long count;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }
}
