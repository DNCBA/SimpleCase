package com.mhc.guava;

import com.google.common.primitives.Chars;
import org.apache.commons.collections4.multiset.HashMultiSet;

public class MutilSetTest {

    public static void main(String[] args) {
        HashMultiSet<Character> multiSet = new HashMultiSet<Character>();

        String str = "itjc8.com&n@YgwbOY@zBWs%S1";
        char[] chars = str.toCharArray();
        Chars.asList(chars).stream().forEach(charItem-> multiSet.add(charItem));

        System.out.println(multiSet.getCount('c'));

    }
}
