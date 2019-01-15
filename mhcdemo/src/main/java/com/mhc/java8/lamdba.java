package com.mhc.java8;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class lamdba {

    //@FunctionalInterface
    public static void main(String[] args) {

        consumer("consumer1",s-> System.out.println(s));
        consumer("consumer2",(s)->{
            System.out.println(s);
        });
        consumer("consumer3",System.out::println);

        predicate("predicate1",(s)->{ return s.equals("aaa");} );
        predicate("aaa", Objects::isNull);
    }

    public static void consumer(Object o,Consumer consumer){
        consumer.accept(o);
    }

    public static Boolean predicate(Object o, Predicate predicate){
        return predicate.test(o);
    }

    public static Object funcation(Object o, Function function){
        return function.apply(o);
    }

    public static Object supplier(Supplier supplier){
        return  supplier.get();
    }


}
