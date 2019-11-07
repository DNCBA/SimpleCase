package com.mhc.poi.easyExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-10-21 13:26
 */
public class UserInfoListener extends AnalysisEventListener<UserInfo> {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoListener.class);

  private List<UserInfo> context = new ArrayList<>();

  @Override
  public void invoke(UserInfo userInfo, AnalysisContext analysisContext) {
    LOGGER.info(JSON.toJSONString(userInfo));
    context.add(userInfo);

  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    LOGGER.info("UserInfoListener doAfterAllAnalysed");
  }


  public List<UserInfo> getContext(){
    return context;
  }
}
