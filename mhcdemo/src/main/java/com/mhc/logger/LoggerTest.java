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


  public static void main(String[] args) {
    String uuid = UUID.randomUUID().toString();
    MDC.put("traceId",uuid);
    MDCAdapter mdcAdapter = MDC.getMDCAdapter();
    mdcAdapter.put("traceId",uuid);

    LOGGER.info("hello self4j");
    testLogger();

  }

  public static void testLogger() {
    LOGGER.info("testLogger");
  }

}
