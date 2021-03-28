package com.mhc.algorithm.jiuzhang.binary;

import com.alibaba.fastjson.JSON;

public class Template {


    public int binarySearchA(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                end = mid;
            } else if (nums[mid] < target) {
                start = mid;
                // or start = mid + 1
            } else {
                end = mid;
                // or end = mid - 1
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


    public int binarySearchB(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int start = 0, end = nums.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                end = mid;
            } else if (nums[mid] < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        if (nums[start] == target) {
            return start;
        }

        return -1;
    }

    public static int minArray(int[] numbers) {
        if (null == numbers || numbers.length == 0) {
            return -1;
        }
        int start = 0;
        int end = numbers.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (numbers[mid] > numbers[end]) {
                start = mid;
            } else {
                end = mid;
            }
        }
        if (numbers[start] > numbers[end]) {
            return numbers[end];
        } else {
            return numbers[start];
        }
    }

    public static int search(int[] nums, int target) {
        if (null == nums || nums.length == 0) {
            return -1;
        }
        int start = 0;
        int end = nums.length - 1;
        while(start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] > nums[start]) {
                if (nums[start] <= target && nums[mid] > target){
                    end = mid - 1;
                } else {
                    start = mid;
                }
            } else {
                if (nums[end] >= target && nums[mid] < target){
                    start = mid + 1;
                } else {
                    end = mid;
                }
            }
        }
        if (nums[start] == target) {
            return start;
        } else {
            return -1;
        }
    }

    public static int searchC(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                end = mid;
            } else if (nums[mid] > target) {
                end = mid;
            } else {
                start = mid;
            }
        }

        int first = -1;
        if(nums[start] == target) {
            first = start;
        } else if (nums[end] == target) {
            first = end;
        } else {
            return 0;
        }

        start = 0;
        end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                start = mid;
            } else if (nums[mid] > target) {
                end = mid;
            } else {
                start = mid;
            }
        }

        int last = -1;
        if(nums[end] == target) {
            last = end;
        } else if (nums[start] == target) {
            last = start;
        } else {
            return 0;
        }

        return last - first + 1;
    }

    public static int[] kClosestNumbers(int[] nums, int target, int k) {
        if (null == nums || nums.length == 0) {
            return new int[]{};
        }

        int start = 0;
        int end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                end = mid;
            } else if (nums[mid] > target) {
                end = mid;
            } else {
                start = mid;
            }
        }

        for (int i = 0; i < k - 2; i++) {
            int left = Math.abs(nums[start] - target);
            int right = Math.abs(nums[end] - target);
            if ( left == right ){
                if (start == 0){
                    end++;
                } else {
                    start--;
                }
            } else if (left > right) {
                if (end == nums.length - 1){
                    start--;
                } else {
                    end++;
                }
            } else {
                if (start == 0){
                    end++;
                } else {
                    start--;
                }
            }
        }




        int[] res = new int[(end - start + 1)];
        for (int i = start; i <= end; i++){
            res[i-start] = nums[i];
        }
        return res;

    }

    public static void main(String[] args) {
//        Template template = new Template();
//        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7};
//        int target = 4;
//        int a = template.binarySearchA(nums, target);
//        System.out.println("A: " + a);
//        int b = template.binarySearchB(nums, target);
//        System.out.println("B: " + b);

        int[] ints = kClosestNumbers(new int[]{1, 2, 3}, 2, 3);
        System.out.println(JSON.toJSONString(ints));

    }
}
