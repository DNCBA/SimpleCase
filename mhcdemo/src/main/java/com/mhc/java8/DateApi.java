package com.mhc.java8;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateApi {

    public static void main(String[] args) {

        LocalDateTime now = LocalDateTime.now();

        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


//        toPerson();
//
//        toComputer();

    }

    private static void toComputer() {
        Instant instant = Instant.now();
        System.out.println("instant:" + instant);
    }

    private static void toPerson() {
        LocalDate localDate = LocalDate.now();
        LocalDate.of(2099, 1, 1);
        localDate.withYear(2018).withMonth(9).withDayOfMonth(30);
        System.out.println("localDate:" + localDate);
        System.out.println("localDate:" + localDate.format(DateTimeFormatter.BASIC_ISO_DATE));

        LocalTime localTime = LocalTime.now();
        LocalTime.of(9,36,7);
        localTime.withHour(9).withMinute(30).withSecond(0);
        System.out.println("localTime:"+localTime);

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime.of(localDate,localTime);
        System.out.println("localDateTime:"+localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

}
