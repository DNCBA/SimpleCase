//package com.mhc.algorithm.jiuzhang;
//
//public class RotatingArray {
//
//    public static int search(int[] nums) {
//        if (null == nums || nums.length <= 1) {
//            return -1;
//        }
//        int start = 0, end = nums.length;
//        while ((start + 1) < end) {
//            int mid = start + (end - start) / 2;
//            if ()
//        }
//
//    }
//
//
//    public int search(int[] nums, int target) {
//        int len = nums.length;
//        int left = 0, right = len-1;
//        while(left <= right){
//            int mid = (left + right) / 2;
//            if(nums[mid] == target)
//                return mid;
//            else if(nums[mid] < nums[right]){
//                if(nums[mid] < target && target <= nums[right])
//                    left = mid+1;
//                else
//                    right = mid-1;
//            }
//            else{
//                if(nums[left] <= target && target < nums[mid])
//                    right = mid-1;
//                else
//                    left = mid+1;
//            }
//        }
//        return -1;
//    }
//
//
//}
