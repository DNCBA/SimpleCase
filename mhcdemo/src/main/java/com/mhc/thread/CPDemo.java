package com.mhc.thread;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.annotations.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-01-28 19:00
 */
public class CPDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(CPDemo.class);

    private AtomicInteger providerCount = new AtomicInteger(0);
    private AtomicInteger consumerCount = new AtomicInteger(0);
    private AtomicInteger productGenerator = new AtomicInteger(0);

    private Integer contextSize = 50;
    private LinkedBlockingDeque<Integer> context = new LinkedBlockingDeque<>(contextSize);
    private ReentrantLock lock = new ReentrantLock();
    private Condition providerCondition = lock.newCondition();
    private Condition consumerCondition = lock.newCondition();

    private ExecutorService providerExecutors = new ThreadPoolExecutor(4, 4, 60, TimeUnit.SECONDS, new SynchronousQueue<>(), r -> new Thread(r, "provider" + providerCount.incrementAndGet()));
    private ExecutorService consumerExecutors = new ThreadPoolExecutor(4, 4, 60, TimeUnit.SECONDS, new SynchronousQueue<>(), r -> new Thread(r, "consumer" + consumerCount.incrementAndGet()));

    @Test
    public void lockCPtest() throws InterruptedException {
        providerStart(9);
        consumerStart(10);
        TimeUnit.MINUTES.sleep(10);
    }

    private void consumerStart(final Integer timeMs) {
        for (int i = 0; i < 4; i++) {
            consumerExecutors.submit(() -> {
                MDC.put("traceId", RandomStringUtils.random(8, true, true));
                while (true) {
                    try {
                        lock.lock();
                        LOGGER.info("thread [{}] consumer lock success", Thread.currentThread().getName());
                        int size = context.size();
                        if (size <= 0) {
                            LOGGER.info("thread [{}] consumer await and signalAll  size [{}]", Thread.currentThread().getName(), size);
                            providerCondition.signalAll();
                            consumerCondition.await();
                            return;
                        }
                        Integer product = null;
                        if (size > 0) {
                            product = context.pop();
                            TimeUnit.MILLISECONDS.sleep(timeMs);
                        }

                        LOGGER.info("thread [{}] consumer {} success  size [{}]", Thread.currentThread().getName(), product, size);
                    } catch (Exception e) {
                        LOGGER.error("exception consumer thread [{}] ", Thread.currentThread().getName(), e);
                    } finally {
                        lock.unlock();
                        LOGGER.info("thread [{}] consumer unlock success", Thread.currentThread().getName());
                    }
                }

            });
        }
    }

    private void providerStart(final Integer timeMs) {
        for (int i = 0; i < 4; i++) {
            providerExecutors.submit(() -> {
                MDC.put("traceId", RandomStringUtils.random(8, true, true));
                while (true) {
                    try {
                        lock.lock();
                        LOGGER.info("thread [{}] provider lock success", Thread.currentThread().getName());
                        int size = context.size();
                        if (size >= contextSize) {
                            LOGGER.info("thread [{}] provider await signalAll size [{}]", Thread.currentThread().getName(), size);
                            consumerCondition.signalAll();
                            providerCondition.await();
                            return;
                        }
                        Integer product = null;
                        if (size < contextSize) {
                            product = this.productGenerator.incrementAndGet();
                            context.add(product);
                            TimeUnit.MILLISECONDS.sleep(timeMs);
                        }
                        LOGGER.info("thread [{}] provider {} success  size [{}]", Thread.currentThread().getName(), product, size);
                    } catch (Exception e) {
                        LOGGER.error("exception provider thread [{}] ", Thread.currentThread().getName(), e);
                    } finally {
                        lock.unlock();
                        LOGGER.info("thread [{}] provider unlock success", Thread.currentThread().getName());
                    }

                }
            });
        }

    }
}
