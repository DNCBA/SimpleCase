package com.mhc.algorithm.jiuzhang.tree;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-03-16 11:20
 */
public class Temp {


    // mergeSort.
    public ListNode sortList(ListNode head) {
        //check
        if (null == head || null == head.next) {
            return head;
        }

        //middle
        ListNode middle = findMiddle(head);


        //divide
        ListNode right = sortList(middle.next);
        middle.next = null;
        ListNode left = sortList(head);

        //conquer
        ListNode result = merge(left,right);

        //result
        return result;
    }

    /**
     * merge.
     **/
    private ListNode merge(ListNode left, ListNode right) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (null != left && null != right) {
            if (left.val < right.val) {
                curr.next = left;
                curr = left;
                left = left.next;
            } else {
                curr.next = right;
                curr = right;
                right = right.next;
            }
        }
        while (null != left) {
            curr.next = left;
            curr = left;
            left = left.next;
        }
        while (null != right) {
            curr.next = right;
            curr = right;
            right = right.next;
        }
        return dummy.next;
    }

    /**
     * findMiddle.
     **/
    private ListNode findMiddle(ListNode node) {
        ListNode dummy = new ListNode(0);
        dummy.next = node;
        ListNode fast = dummy;
        ListNode slow = dummy;
        while (null != fast && null != fast.next) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static void main(String[] args) {
        ListNode listNode11 = new ListNode(1);
        ListNode listNode12 = new ListNode(-1);
        listNode11.next = listNode12;


        Temp temp = new Temp();
        ListNode listNode = temp.sortList(listNode11);

        print(listNode);


    }

    private static void print(ListNode listNode) {
        while (null != listNode) {
            System.out.print(listNode.val + "->");
            listNode = listNode.next;
        }
    }
}
