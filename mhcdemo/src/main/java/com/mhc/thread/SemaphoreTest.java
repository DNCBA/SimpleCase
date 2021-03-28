package com.mhc.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-03-26 11:01
 */
public class SemaphoreTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SemaphoreTest.class);


    public static void main(String[] args) throws InterruptedException {

        Semaphore semaphore = new Semaphore(10);

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(20, 20, 1, TimeUnit.MINUTES, new SynchronousQueue<>());

        for (int i = 0; i < 10; i++) {
            semaphore.acquire();
            LOGGER.info("acquire success");
            try {
                poolExecutor.submit(() -> {
                    try {
                        LOGGER.info(Thread.currentThread().getName());
                        TimeUnit.SECONDS.sleep(4);
                    } catch (Exception e) {
                        LOGGER.error("exception when process", e);
                    } finally {
                        LOGGER.info("release success");
                        semaphore.release();
                    }
                });
            } catch (Exception e) {
                LOGGER.error("exception when submit", e);
            }
            TimeUnit.SECONDS.sleep(1);
        }


    }
}
