package com.mhc.java8;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

public class OptionalTest {


    public static void main(String[] args) throws Throwable {

        //三种创建方式
        Optional.empty(); //创建一个没有引用的对象
        Optional.of("tom"); //创建一个非空引用的对象
        Optional optional = Optional.ofNullable(null);  //创建一个可能为空的对象

        //判读为空
        optional.isPresent(); //是否存在引用
        optional.ifPresent(System.out::println); //只有存在的时候执行

        //当为空的时候自定义的逻辑
        optional.orElse("is null");
        optional.orElseGet(()->"is null get");
//        optional.orElseThrow(()-> new RuntimeException("is null throw"));

        testNullList(null);
    }

    private static void testNullList(ArrayList<Object> arrayList) {
        Optional.ofNullable(arrayList)
                .orElseGet(ArrayList::new)
                .stream()
                .forEach(System.out::println);
    }


}
