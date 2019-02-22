package com.mhc.algorithm.impl;

import com.mhc.algorithm.Sorts;

/**
 * 插入排序
 * 假设前面的数据是有序的，从后面每一个数据向前插入
 */
public class InsertSort implements Sorts {


    @Override
    public void sortArray(Integer[] array) {

        if (array.length < 2 || null == array){
            return;
        }

        for (int i = 1 ;i < array.length ; i++ ){
            int minIndex = i;
            while ( minIndex > 0 && array[minIndex -1 ] > array[minIndex]){
                swap(array,minIndex,minIndex-1);
                minIndex = minIndex -1;
            }
        }



    }


}
