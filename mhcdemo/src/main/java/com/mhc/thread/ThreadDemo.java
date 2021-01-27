package com.mhc.thread;

import org.testng.annotations.Test;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadDemo {


    @Test
    public void testLockSleep() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            try {
                System.out.println("t1 start");
                lock.lock();
                System.out.println("t1 lock");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t1 sleep finish");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("t1 unlock");
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                System.out.println("t2 start");
                lock.lock();
                System.out.println("t2 lock");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t2 sleep finish");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("t1 unlock");
            }
        });
        t1.start();
        t2.start();
        TimeUnit.SECONDS.sleep(2);
        t1.interrupt();
        TimeUnit.SECONDS.sleep(50);
    }


    @Test
    public void testInterrupted() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t1 finish");
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("t1 interrupted");
            }
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t1 interrupted finish");
            } catch (InterruptedException e) {
                System.out.println("t1 finish");
            }
        });

        t1.start();
        TimeUnit.SECONDS.sleep(1);
        t1.interrupt();

        TimeUnit.SECONDS.sleep(10);
    }
}
