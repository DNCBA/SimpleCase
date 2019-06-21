package com.mhc.base;

import com.alibaba.fastjson.JSON;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {


    public static void main(String[] args) {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("aaa,","bbb");
        stringStringHashMap.put("ccc","ddd");
        String s = JSON.toJSONString(stringStringHashMap);
        System.out.println(s);
    }


    @Test
    public void store(){
        Exception exception = new NotFoundFormInstanceException();
        System.out.println(exception.getMessage());
    }
}
