package com.mhc.guava;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.collections.list.UnmodifiableList;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImmutableTest {

    public static void main(String[] args) {
        List<Double> list = Stream.generate(Math::random).limit(10).collect(Collectors.toList());

//        List<Double> unmodifiableList = Collections.unmodifiableList(list);
        ImmutableList<Double> unmodifiableList = ImmutableList.copyOf(list);
        testModifiable(unmodifiableList);
        System.out.println(list);
    }

    private static void testModifiable(List<Double> unmodifiableList) {
        unmodifiableList.add(1D);
    }
}
