package com.mhc.java8;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Stream {

    public static void main(String[] args) {
        List<Ticket> tickets = Arrays.asList(new Ticket[]{
                new Ticket(1,true,"100"),
                new Ticket(2,false,"200"),
                new Ticket(3,true,"300"),
                new Ticket(4,false,"400"),
                new Ticket(5,true,"500")
        });




        List<Integer> collect = tickets.stream().filter(Ticket::getActive).map(Ticket::getId).sorted().collect(Collectors.toList());
        System.out.println(collect);

        Map<Boolean, List<Ticket>> collect1 = tickets.stream().collect(Collectors.groupingBy(Ticket::getActive));
        System.out.println(collect1);


        tickets.stream().filter( ticket -> ticket.getId() > 3 ).forEach(System.out::println);
    }

}
