package com.mhc.algorithm;

import java.util.*;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-03-31 20:46
 */
public class Temp {

    public void sortColors(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            while (left < right && nums[right] > 1) {
                right--;
            }
            while (left < right && nums[left] < 1) {
                left++;
            }
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            if (nums[left] == nums[right]) {
                right--;
            }
        }
    }

    public static void main(String[] args) {
        Temp temp = new Temp();
        temp.sortColors(new int[]{2,0,2,2,1,2,2,1,2,0,0,0,1});
    }

}

