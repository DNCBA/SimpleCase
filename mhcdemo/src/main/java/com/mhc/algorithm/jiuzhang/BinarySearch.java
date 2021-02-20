package com.mhc.algorithm.jiuzhang;

import org.junit.Assert;

public class BinarySearch {


    public static int binarySearchFindFirst(int[] nums, int target) {
        if (null == nums || nums.length == 0) {
            return -1;
        }
        int start = 0, end = nums.length - 1;
        while ((start + 1) < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                end = mid;
            } else if (nums[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (nums[start] == target) {
            return start;
        }
        if (nums[end] == target) {
            return end;
        }
        return -1;
    }


    public static int binarySearchFindLast(int[] nums, int target) {
        if (null == nums || nums.length == 0) {
            return -1;
        }
        int start = 0, end = nums.length - 1;
        while ((start + 1) < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target){
                start = mid;
            } else if (nums[mid] < target){
                start = mid;
            } else {
                end = mid;
            }
        }
        if (nums[end] == target) {
            return end;
        }
        if (nums[start] == target) {
            return start;
        }
        return -1;
    }


    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 3, 3, 3};
        int target = 3;
        int result = binarySearchFindFirst(nums, target);
        Assert.assertEquals(2, result);
        System.out.println(result);
        result = binarySearchFindLast(nums, target);
        Assert.assertEquals(5, result);
        System.out.println(result);
    }


}
