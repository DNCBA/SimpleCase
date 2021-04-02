package com.mhc.algorithm;

import java.util.*;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-03-31 20:46
 */
public class Temp {

    public int maxSubArray(int[] nums) {
        //define
        int[] dp = new int[nums.length];

        //init
        dp[0] = nums[0];
        int result = dp[0];

        //function
        for (int i = 1; i < nums.length; i++) {
           dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
        }

        //result
        return result;
    }


    private int sum(int[] nums, int start, int end) {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            sum = sum + nums[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        Temp temp = new Temp();
        int i = temp.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
        System.out.println(i);
    }

}

