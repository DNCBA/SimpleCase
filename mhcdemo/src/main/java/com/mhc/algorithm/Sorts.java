package com.mhc.algorithm;


public interface Sorts {

    void sortArray(Integer[] array);


    default  void swap(Integer[] array , int start ,int end){
        int temp = array[start];
        array[start] = array[end];
        array[end] = temp;
    }

}
