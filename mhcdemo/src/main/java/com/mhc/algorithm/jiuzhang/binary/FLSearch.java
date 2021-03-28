package com.mhc.algorithm.jiuzhang.binary;

public class FLSearch {

    public int[] searchRange(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                end = mid;
            } else if (nums[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }

        int first = -1;
        if (nums[start] == target) {
            first = start;
        } else if (nums[end] == target) {
            first = end;
        }

        start = 0;
        end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                start = mid;
            } else if (nums[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }
        int last = -1;
        if (nums[end] == target) {
            last = end;
        } else if (nums[start] == target) {
            last = start;
        }
        return new int[]{first, last};
    }


    public static void main(String[] args) {
        FLSearch flSearch = new FLSearch();
        int[] ints = flSearch.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8);
        System.out.println(ints[0] + "" + ints[1]);
    }


}
