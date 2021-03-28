package com.mhc.algorithm.jiuzhang;

import java.util.*;

public class Temp {


    public ListNode partition(ListNode head, int x) {
        ListNode slowDump = new ListNode(-1);
        ListNode slow = slowDump;
        ListNode hightDump = new ListNode(-1);
        ListNode hight = hightDump;

        ListNode curr = head;
        while (null != curr) {
            if (x >= curr.val) {
                slow.next = curr;
                slow = curr;
            } else {
                hight.next = curr;
                hight = curr;
            }
            curr = curr.next;
        }

        hight.next = null;
        slow.next = hightDump.next;

        return slowDump.next;

    }


    public static void main(String[] args) {
        ListNode listNode3 = new ListNode(3);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode1 = new ListNode(1);

        listNode3.next = listNode2;
        listNode2.next = listNode1;

        Temp temp = new Temp();
        ListNode partition = temp.partition(listNode3, 2);

        while (null != partition) {
            System.out.println(partition.val);
            partition = partition.next;
        }


    }
}
