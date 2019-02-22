package com.mhc.algorithm.impl;

import com.mhc.algorithm.Sorts;

/**
 * 选择排序的具体实现
 * 每次遍历都找到剩下数据的最小值，并将数据放到最前方进行排序
 */
public class SelectionSort implements Sorts {


    @Override
    public void sortArray(Integer[] array) {
        for (int i = 0 ; i< array.length;i++){
            int minIndex = i;
            int minValue = array[i];
            for (int j = i; j< array.length;j++){
                if (minValue > array[j]){
                    minIndex = j;
                    minValue = array[j];
                }
            }
            swap(array,i,minIndex);
        }
    }
}
