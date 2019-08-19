package com.mhc.base;

import com.alibaba.fastjson.JSON;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Test
    public void test(){
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("1");
        objects.add(2);
        objects.add(3);
        System.out.println(objects);
        objects.add(0,"0");
        Object remove = objects.remove(2);
        objects.add(0,remove);
        System.out.println(objects);
    }
}
