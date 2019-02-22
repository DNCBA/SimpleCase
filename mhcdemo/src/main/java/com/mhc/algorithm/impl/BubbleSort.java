package com.mhc.algorithm.impl;

import com.mhc.algorithm.Sorts;

/**
 * 冒泡排序的具体实现
 * 遍历数据，两两对比，将最大的数据向后转移，
 */
public class BubbleSort implements Sorts {

    @Override
    public void sortArray(Integer[] array) {
        for (int i = array.length-1;i>0;i--){
            for (int j=0;j<i;j++){
                if (array[j]>array[j+1]){
                    swap(array,j,j+1);
                }
            }
        }
    }


}
