package com.mhc.java8;

import com.mhc.jdbc.User;
import org.joda.time.Interval;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {

    /**
     * reducing(初始值，转换函数，累积函数)
     * @param args
     */
    public static void main(String[] args) {
//        Stream<Integer> stream = createStream();
////        Map<String, Long> collect = stream.filter(t -> t > 5)
////                .collect(Collectors.groupingBy(Object::toString, Collectors.counting()));
////        System.out.println(collect);
//
////        Integer sum = stream.filter(t -> t > 5)
////                .map(Integer::valueOf)
////                .collect(Collectors.summingInt(Integer::new));
//
//
//        Integer sum = stream.filter(t -> t > 5)
//                .map(Objects::toString)
//                .collect(Collectors.reducing(0,
//                        Integer::parseInt,
//                        (a, b) -> a + b));
//
//        System.out.println(sum);
//
//        User user = new User();

    }


    public static Stream createStream() {


        Stream streamInstance = null;

        List<Integer> list = Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 5, 8});
        streamInstance = list.stream();

//        streamInstance = Stream.of(1,2,3,4,5,6,7,8,9,10,1);
//
//        streamInstance = Arrays.stream(new Integer[]{1,2,3,4,5,6,7,8,9,1,2,5,8});
//
//        streamInstance = Stream.iterate(0, n -> n +2) ;
//
//        streamInstance = Stream.generate(Math::random);

        return streamInstance;
    }

}
