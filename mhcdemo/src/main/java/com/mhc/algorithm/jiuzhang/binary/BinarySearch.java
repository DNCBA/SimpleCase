package com.mhc.algorithm.jiuzhang.binary;

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


    public static boolean findNumberIn2DArray(int[][] matrix, int target) {
        if (null == matrix || matrix.length == 0 || matrix[0].length == 0){
            return false;
        }
        int xStart = 0;
        int yStart = 0;
        int xEnd = matrix.length;
        int yEnd = matrix[0].length;
        while (xStart < xEnd || yStart < yEnd) {
            int xMid = xStart + (xEnd - xStart) / 2;
            int yMid = yStart + (yEnd - yStart) / 2;
            if (matrix[xMid][0] == target) {
                return true;
            } else if (matrix[xMid][0] > target) {
                xEnd = xMid - 1;
            } else {
                xStart = xMid + 1;
            }

            if (matrix[0][yMid] == target) {
                return true;
            } else if (matrix[0][yMid] > target) {
                yEnd = yMid - 1;
            } else {
                yStart = yMid + 1;
            }
        }

        if(matrix[xStart][yStart] == target) {
            return true;
        } else {
            return false;
        }
    }


    public static void main(String[] args) {
        int[][] nums = {
                {1,4,7,11,15},
                {2,5,8,12,19},
                {3,6,9,16,22},
                {10,13,14,17,24},
                {18,21,23,26,30}};
        int target = 5;
        findNumberIn2DArray(nums,target);
    }


}
