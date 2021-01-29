package com.mhc.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.sql.Time;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-01-28 16:30
 */
public class LockDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(LockDemo.class);


    @Test
    public void testCondition2() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread t = new Thread(() -> {
            try {
                lock.lock();
                LOGGER.info("testCondition2 lock success");
                condition.await();
                LOGGER.info("testCondition2 signal");
            } catch (Exception e) {
                LOGGER.error("exception testCondition2", e);
            } finally {
                lock.unlock();
                LOGGER.info("testCondition2 unlock");
            }
        }, "t");


        t.start();


        Thread get = new Thread(() -> {
            try {
                LOGGER.info("get T lock");
                lock.lock();
                LOGGER.info("get T lock success");
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                LOGGER.error("exception get T ", e);
            } finally {
                lock.unlock();
                LOGGER.info("get T unlock");
            }
        });

        TimeUnit.SECONDS.sleep(5);
        try {
            lock.lock();
            LOGGER.info("currentThread lock success");
            TimeUnit.SECONDS.sleep(1);
            condition.signalAll();
            get.start();
            LOGGER.info("currentThread signalAll");
        } finally {
            lock.unlock();
            LOGGER.info("currentThread unlock");
        }




        TimeUnit.SECONDS.sleep(10);
    }


    @Test
    public void testCondition() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        Condition c1 = lock.newCondition();
        Condition c2 = lock.newCondition();


        for (int i = 0; i < 4; i++) {
            Thread t = new Thread(() -> {
                try {
                    try {
                        lock.lock();
                        LOGGER.info("thread [{}] lock success", Thread.currentThread().getName());
                        c1.await();
                        TimeUnit.SECONDS.sleep(5);
                        c1.signal();
                        LOGGER.info("thread [{}] c1 signal success", Thread.currentThread().getName());
                    } catch (Exception e) {
                        LOGGER.error("exception {} ", Thread.currentThread().getName(), e);
                    } finally {
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        lock.unlock();
                        LOGGER.info("thread [{}] unlock", Thread.currentThread().getName());
                    }
                } catch (Exception e) {

                }
            });
            t.setName("1t:" + i);
            t.start();
        }


        for (int i = 0; i < 4; i++) {
            Thread t = new Thread(() -> {
                try {
                    try {
                        lock.lock();
                        LOGGER.info("thread [{}] lock success", Thread.currentThread().getName());
                        c2.await();
                        TimeUnit.SECONDS.sleep(5);
                        c2.signal();
                        LOGGER.info("thread [{}] c2 signal success", Thread.currentThread().getName());
                    } catch (Exception e) {
                        LOGGER.error("exception {} ", Thread.currentThread().getName(), e);
                    } finally {
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        lock.unlock();
                        LOGGER.info("thread [{}] unlock", Thread.currentThread().getName());
                    }
                } catch (Exception e) {

                }
            });
            t.setName("2t:" + i);
            t.start();
        }


        Thread n1 = new Thread(() -> {
            try {
                lock.lock();
                LOGGER.info("thread [{}] lock success", Thread.currentThread().getName());
                c1.signal();
                LOGGER.info("thread [{}] c1 signal success", Thread.currentThread().getName());
            } catch (Exception e) {
                LOGGER.error("exception {} ", Thread.currentThread().getName(), e);
            } finally {
                lock.unlock();
                LOGGER.info("thread [{}] unlock", Thread.currentThread().getName());
            }

        });
        n1.setName("n1");


        Thread n2 = new Thread(() -> {
            try {
                lock.lock();
                LOGGER.info("thread [{}] lock success", Thread.currentThread().getName());
                c2.signal();
                LOGGER.info("thread [{}] c2 signal success", Thread.currentThread().getName());
            } catch (Exception e) {
                LOGGER.error("exception {} ", Thread.currentThread().getName(), e);
            } finally {
                lock.unlock();
                LOGGER.info("thread [{}] unlock", Thread.currentThread().getName());
            }

        });
        n2.setName("n2");


        n1.start();
        n2.start();


        TimeUnit.MINUTES.sleep(5);

    }


    @Test
    public void testLockFair() throws InterruptedException {


        ReentrantLock lock = new ReentrantLock();
        CountDownLatch countDownLatch = new CountDownLatch(1);


        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(() -> {
                try {
                    lock.lock();
                    LOGGER.info("thread [{}] lock success", Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(5);

                } catch (Exception e) {
                    LOGGER.error("exception {} ", Thread.currentThread().getName(), e);
                } finally {
                    try {
                        countDownLatch.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    LOGGER.info("unlock" + System.currentTimeMillis());
                    lock.unlock();
                }
            });
            t.setName("t" + i);
            t.start();
        }

        for (int i = 0; i < 50; i++) {
            Thread currT = new Thread(() -> {
                try {
                    countDownLatch.await();
                    lock.lock();
                    LOGGER.info("lock" + System.currentTimeMillis());
                    LOGGER.info("thread [{}] lock success", Thread.currentThread().getName());
                } catch (Exception e) {
                    LOGGER.error("exception {} ", Thread.currentThread().getName(), e);
                } finally {
                    lock.unlock();
                }
            });
            currT.setName("i" + i);
            currT.start();
        }

        TimeUnit.MINUTES.sleep(10);
    }
}
