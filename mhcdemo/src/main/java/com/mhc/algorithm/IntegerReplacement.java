package com.mhc.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-08-06 19:47
 */
public class IntegerReplacement {



  public static void main(String[] args) {
    int result = solution(65535);
    System.out.println(result);
  }


  //fixme 用力 65535 无法通过 期望 17
  private static int solution(int n) {
    int count = 0;
    char[] chars = Integer.toBinaryString(n).toCharArray();
    if (chars.length == 1){
      return count;
    }
    int lastPoint = Integer.valueOf(String.valueOf(chars[chars.length-1]));
    if (Objects.equals(1, lastPoint)){
      count++;
      count += solution(n-1);
    }
    if (Objects.equals(0, lastPoint)){
      count++;
      count += solution(n/2);
    }
    return count;
  }

  private static Character[] convertToCharacter(char[] chars) {
    Character[] characters = new Character[chars.length];
    for (int i = 0; i < chars.length; i++) {
      characters[i] = new Character(chars[i]);
    }
    return characters;
  }

}
