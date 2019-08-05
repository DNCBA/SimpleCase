package com.mhc.algorithm;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-08-05 21:32
 *
 * 求能获得最大利润
 *
 */
public class MaxProfit {

  private static final Logger LOGGER = LoggerFactory.getLogger(MaxProfit.class);

  public static void main(String[] args) {

    Integer[] array = TestUtils.createIntArray(5, 100);
    LOGGER.info("生成数组:"+ JSON.toJSONString(array));

    int result = solution(array);

    LOGGER.info("结果:"+result );

  }

  private static int solution(Integer[] array) {
    if (null == array || array.length == 0) return 0;
    int result = 0;
    for (int i = 0; i < array.length - 1; i++) {
      if (array[i] < array[i+1] ) {
        result += array[i+1]- array[i];
      }
    }
    return result;
  }

}
