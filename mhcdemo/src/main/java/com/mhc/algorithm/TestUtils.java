package com.mhc.algorithm;

import java.util.Arrays;
import java.util.Collections;
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

    //指定次数和长度的测试

    public static void testSort(int count,int size,int range,Sorts sorts) {
        for (int i = 0; i < count; i++) {
            //step1:产生数据源
            Integer[] array = TestUtils.createIntArray(size, range);
            Integer[] data = Arrays.copyOf(array, array.length);
            Integer[] copy = Arrays.copyOf(array, array.length);

            //step2:两种算法的实现
            sorts.sortArray(array);
            Arrays.sort(copy);
            //step3：对比输出结果
            if (!Arrays.asList(copy).equals(Arrays.asList(array))) {
                System.err.println("正确结果:" + Arrays.asList(copy) + "\r\n错误结果:" + Arrays.asList(array) + "\r\n原始数据:" + Arrays.asList(data));
            }
        }
    }


}
