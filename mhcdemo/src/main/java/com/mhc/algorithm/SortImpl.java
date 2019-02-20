package com.mhc.algorithm;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SortImpl {

    public static void main(String[] args) {

        for (int i=0;i<1000;i++) {
            //step1:产生数据源
            Integer[] array = TestUtils.createIntArray(500, 200);
            Integer[] copy = Arrays.copyOf(array, array.length);
            List<Integer> list = Arrays.asList(copy);
            //step2:两种算法的实现
            //bubbleSort(array);
            selectionSort(array);
            Collections.sort(list);
            //step3：对比输出结果
            if (!list.equals(Arrays.asList(array))) {
                System.err.println("正确结果:" + list + "\r\n错误结果:" + Arrays.asList(array)+"\r\n原始数据:"+ Arrays.asList(copy));
            }
        }

    }

    public static void bubbleSort(Integer[] array) {
        if (array.length < 2) {
            return;
        }
        for (int i = array.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                }
            }
        }
    }

    public static void selectionSort(Integer[] array){
        if (array.length < 2){
            return;
        }

        for (int i=0;i<array.length;i++){
            int minIndex = i;
            int minData = array[i];
            for (int j = i;j<array.length;j++){
                if (array[j] < minData){
                    minData = array[j];
                    minIndex = j;
                }
            }
            swap(array,i,minIndex);
        }
    }

    public static void swap(Integer[] array, final Integer start, final Integer end) {
        Integer temp = array[start];
        array[start] = array[end];
        array[end] = temp;
    }


}
