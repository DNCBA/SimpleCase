package com.mhc.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-07-02 10:58
 */
public class LockTest {

   volatile Integer count = 10;

  public static void main(String[] args) {

    LockTest lockTest = new LockTest();

    lockTest.testReentrantLock();

    lockTest.testSynchroized();

    lockTest.testVoliate();

  }

  public  void testVoliate() {
    CountDownLatch countDownLatch = new CountDownLatch(20);
    ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(50);
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 20, 10, TimeUnit.SECONDS,
        arrayBlockingQueue);

    for (int i = 0; i < 20; i++) {
      threadPoolExecutor.submit(() -> {
        try {
          countDownLatch.countDown();
          System.out.println(Thread.currentThread().getName() +" -1 now:" + countDownLatch.getCount());
          countDownLatch.await();
          count++;
          System.out.println(Thread.currentThread().getName() + ":" + count);
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    }
  }

  public  void testSynchroized() {
    Object o = new Object();
    synchronized (o) {
      System.out.println("synchronized 1 success");
      synchronized (o) {
        System.out.println("synchronized 2 success");
      }
    }
    System.out.println("synchronized end ");
  }

  public  void testReentrantLock() {
    ReentrantLock reentrantLock = new ReentrantLock();
    if (reentrantLock.tryLock()) {
      System.out.println("reentrantLock lock 1 success");
      System.out.println(reentrantLock.isLocked());
      if (reentrantLock.tryLock()) {
        System.out.println("reentrantLock lock 2 success");
        System.out.println(reentrantLock.isLocked());
      }
    }
    reentrantLock.unlock();
    System.out.println("reentrantLock unlock success");
  }

}
