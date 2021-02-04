package com.mhc.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.concurrent.*;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-02-04 19:25
 */
public class ThreadPoolDemo {

    public static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolDemo.class);


    @Test
    public void testCallable() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService pool = Executors.newCachedThreadPool();

        Future<String> future = pool.submit(() -> {
            LOGGER.info("Thread running start ");
            TimeUnit.SECONDS.sleep(10);
            LOGGER.info("Thread running end ");
            return "result";
        });

        TimeUnit.SECONDS.sleep(2);
        LOGGER.info("future get start ");
        String result = future.get();
        LOGGER.info("future get end : {}", result);

        TimeUnit.MINUTES.sleep(2);
    }

    @Test
    public void testCallableTimeOut() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService pool = Executors.newCachedThreadPool();

        Future<String> future = pool.submit(() -> {
            LOGGER.info("Thread running start ");
            TimeUnit.SECONDS.sleep(10);
            LOGGER.info("Thread running end ");
            return "result";
        });

        TimeUnit.SECONDS.sleep(2);
        LOGGER.info("future get start ");
        String result = future.get(5, TimeUnit.SECONDS);
        LOGGER.info("future get end : {}", result);

        TimeUnit.MINUTES.sleep(2);
    }


    @Test
    public void testCallableException() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService pool = Executors.newCachedThreadPool();

        Future<String> future = pool.submit(() -> {
            LOGGER.info("Thread running start ");
            TimeUnit.SECONDS.sleep(10);
            LOGGER.info("Thread running end ");
            throw new IllegalStateException("exception");
//            return "result";
        });

        TimeUnit.SECONDS.sleep(2);
        LOGGER.info("future get start ");
        String result = null;
        try {
            result = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("future get end : {}", result);
        TimeUnit.MINUTES.sleep(2);
    }
}
