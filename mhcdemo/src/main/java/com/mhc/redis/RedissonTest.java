package com.mhc.redis;

import org.apache.commons.lang3.RandomStringUtils;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-02-08 15:42
 */
public class RedissonTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonTest.class);

    private RedissonClient redissonClient;


    @BeforeTest
    public void initConfig() {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec());
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        this.redissonClient = Redisson.create(config);
    }


    @Test
    public void testBucket() throws InterruptedException {
        RBucket<Object> bucket = redissonClient.getBucket("bucket1");
        RLock lock = redissonClient.getLock("local4");


        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                try {
                    lock.lock();
                    Object result = bucket.get();
                    if (null == result) {
                        bucket.set(Thread.currentThread().getName());
                    }
                    Object out = bucket.get();
                    LOGGER.info(out.toString());
                } catch (Exception e) {
                    LOGGER.error("exception ", e);
                } finally {
                    lock.unlock();
                }
            });
            t.setName(i + "");
            t.start();
        }

        TimeUnit.MINUTES.sleep(5);
    }


    @Test
    public void testTryLock() throws InterruptedException {
        RLock lock = redissonClient.getFairLock("lock2");
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                MDC.put("traceId", RandomStringUtils.random(8, true, true));
                try {
                    LOGGER.info("tryLock start");
                    if (lock.tryLock(5, TimeUnit.SECONDS)) {
                        LOGGER.info("tryLock Success");
                    }
                    TimeUnit.SECONDS.sleep(10);
                    LOGGER.info("thread finish");
                } catch (Exception e) {
                    LOGGER.error("exception ", e);
                }
            });
            t.setName(i + "");
            t.start();
        }
        TimeUnit.MINUTES.sleep(5);

    }

    @Test
    public void testGetLock() throws InterruptedException {
        RLock lock = redissonClient.getLock("lock1");
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                MDC.put("traceId", RandomStringUtils.random(8, true, true));
                try {
                    LOGGER.info("lock start");
                    lock.lock();
                    LOGGER.info("lock success");
                    TimeUnit.SECONDS.sleep(10);
                } catch (Exception e) {
                    LOGGER.error("exception ", e);
                } finally {
                    lock.unlock();
                    LOGGER.info("unlock success");
                }
                MDC.clear();
            });
            t.setName(i + "");
            t.start();
        }
        TimeUnit.MINUTES.sleep(5);
    }


}
