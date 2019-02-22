package com.mhc.algorithm;

import com.mhc.algorithm.impl.BubbleSort;
import com.mhc.algorithm.impl.InsertSort;
import com.mhc.algorithm.impl.SelectionSort;

public class Bootstrap {


    public static void main(String[] args) {

        //TestUtils.testSort(20000,150,2000,new BubbleSort());

        //TestUtils.testSort(20000,150,2000,new SelectionSort());

        TestUtils.testSort(1,5,10,new InsertSort());

    }

}
