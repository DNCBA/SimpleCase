package com.mhc.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.spi.MDCAdapter;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-08-10 15:39
 */
public class LoggerTest {


  private  static final Logger LOGGER = LoggerFactory.getLogger(LoggerTest.class);

  private static final org.apache.log4j.Logger LOGGER2 = org.apache.log4j.Logger.getLogger(LoggerTest.class);


  public static void main(String[] args) {
     testSelfLogger();

     testLog4J();

  }

  private static void testLog4J() {
    LOGGER2.info("log4j hello self4j");
    LOGGER2.info("log4j testLogger");
  }

  public static void testSelfLogger() {
    LOGGER.info("self4j hello self4j");
    LOGGER.info("self4j testLogger");
  }

}
