package com.mhc.offer.part01;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 *
 * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
 * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
public class Main {

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    Integer target = 1;
    int[][] array = new int[][]{{1,2,3},{3,3,4},{3,4,5}};
    Boolean result = find(target,array);
  }

  private static Boolean find(Integer target, int[][] array) {
    if (null == target || null == array){
      return false;
    }
    for (int i= 0;i<array.length;i++){
      for (int j = 0;j<array[i].length;j++){
        if (target == array[i][j]){
          return true;
        }
      }
    }
    return false;
  }
}
