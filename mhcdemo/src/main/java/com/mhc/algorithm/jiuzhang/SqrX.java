package com.mhc.algorithm.jiuzhang;

import org.junit.Assert;

public class SqrX {

    public static int sqrX(int target) {
        if (target == 0 || target == 1) {
            return target;
        }
        int start = 0, end = target;
        while ((start + 1) < end) {
            int mid = start + (end - start) / 2;
            if ((long) mid * mid == target) {
                end = mid;
            } else if ((long) mid * mid < target) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (start * start == target) {
            return start;
        }
        if (end * end == target) {
            return end;
        }
        return start;
    }

    public static void main(String[] args) {
        int x = 2147395599;
        int result = sqrX(x);
        System.out.println(result);
        Assert.assertEquals(result, 46339);
    }

}
