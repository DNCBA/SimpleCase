package com.mhc.algorithm;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestUtils {

    //用来产生一个指定长度和制定范围的随机数组
    public static Integer[] createIntArray(int size, int range) {
        List<Integer> collect = Stream
                .generate(() -> (Math.random() - Math.random()) * range)
                .map(t -> new Double(t).intValue())
                .limit(size).collect(Collectors.toList());

        Integer[] array = collect.toArray(new Integer[]{});
        //System.out.println("随机数组长度:" + size + ",范围:" + range + ",数据:" + collect);
        return array;
    }


}
