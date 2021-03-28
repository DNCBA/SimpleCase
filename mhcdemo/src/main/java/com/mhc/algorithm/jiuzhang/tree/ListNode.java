package com.mhc.algorithm.jiuzhang.tree;

import lombok.Data;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-03-22 17:32
 */
@Data
public class ListNode {
    int val;
    ListNode next;
    ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}
