package com.mhc.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-02-04 20:40
 */
public class ExecutorsDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutorsDemo.class);


    @Test
    public void testReject() throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(1));

        for (int i = 0; i < 10; i++) {
            LOGGER.info("submit {}", i);
            try {
                executorService.submit(() -> {
                    try {
                        LOGGER.info("{} start", Thread.currentThread().getName());
                        TimeUnit.SECONDS.sleep(5);
                        LOGGER.info("{} end", Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        LOGGER.error("exception ", e);
                        Thread.interrupted();
                    }
                });
            } catch (Exception e) {
                LOGGER.error("exception submit ", e);
            }
        }

        TimeUnit.MINUTES.sleep(5);
    }
}
