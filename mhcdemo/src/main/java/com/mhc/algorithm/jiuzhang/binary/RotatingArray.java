package com.mhc.algorithm.jiuzhang.binary;

public class RotatingArray {


    public int searchB(int[] nums ,int target){
        int start = 0, end = nums.length -1;
        while ((start + 1) < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < nums[end]){
                if (nums[mid] < target && target <= nums[end]){
                    start = mid;
                }else {
                    end = mid;
                }
            } else {
                if (nums[start] <= target && target < nums[mid]){
                    end = mid;
                }else {
                    start = mid;
                }
            }
        }
        return -1;

    }


}
