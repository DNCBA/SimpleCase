package com.mhc.java8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateApi.class);

    public static void main(String[] args) {



//        testDateShow();


        testDateParse();


//        testDateConvert();




    }

    private static void testDateConvert() {
        //instant->every
        Instant instant = Instant.now();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        OffsetDateTime offsetDateTime = OffsetDateTime.ofInstant(instant, ZoneId.systemDefault());

        LOGGER.info("localDateTime : {}", localDateTime);
        LOGGER.info("zonedDateTime : {}", zonedDateTime);
        LOGGER.info("offsetDateTime : {}", offsetDateTime);
        LOGGER.info("instant : {}", instant);


        //every->instant
        Instant instant_local = LocalDateTime.now().toInstant(ZoneOffset.of("+8"));
        Instant instant_zoned = ZonedDateTime.now().toInstant();
        Instant instant_offset = OffsetDateTime.now().toInstant();
        LOGGER.info("instant_local : {}", instant_local);
        LOGGER.info("instant_zoned : {}", instant_zoned);
        LOGGER.info("instant_offset : {}", instant_offset);


    }

    private static void testDateParse() {

        Instant instant = Instant.parse("2022-01-28T02:25:19.650Z");
        Instant instant1 = Instant.ofEpochMilli(1643336719611L);
        Instant instant2 = Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse("2022-01-28T10:25:19.650+08:00"));
        LocalDateTime localDateTime = LocalDateTime.parse("2022-01-28T10:25:19.649");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2022-01-28T10:25:19.650+08:00[Asia/Shanghai]");
        OffsetDateTime offsetDateTime = OffsetDateTime.parse("2022-01-28T10:25:19.650+08:00");

        LOGGER.info("instant1 : {}", instant1);
        LOGGER.info("instant2 : {}", instant2);
        LOGGER.info("localDateTime : {}", localDateTime);
        LOGGER.info("zonedDateTime : {}", zonedDateTime);
        LOGGER.info("offsetDateTime : {}", offsetDateTime);
        LOGGER.info("instant : {}", instant);

    }

    private static void testDateShow() {
        //时间戳
        long currentTimeMillis = System.currentTimeMillis();
        //localDateTime
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.systemDefault());
        //zonedDateTime
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault());
        //offsetDateTime
        OffsetDateTime offsetDateTime = OffsetDateTime.now(ZoneId.systemDefault());
        //instant
        Instant instant = Instant.now();

        LOGGER.info("currentTimeMillis : {}", currentTimeMillis);
        LOGGER.info("localDateTime : {}", localDateTime);
        LOGGER.info("zonedDateTime : {}", zonedDateTime);
        LOGGER.info("offsetDateTime : {}", offsetDateTime);
        LOGGER.info("instant : {}", instant);
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
