package com.mhc.thread;

import org.testng.annotations.Test;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SynchronizedDemo {


    @Test
    public void testWait() throws InterruptedException {
        Object o = new Object();
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(() -> {
                synchronized (o) {
                    System.out.println(Thread.currentThread().getName() + "加锁成功");
                    try {
                        o.wait();
                        TimeUnit.SECONDS.sleep(3);
                        o.notify();
//                        o.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "执行完毕");
                }
            });
            t.setName(i + "");
            t.start();
        }
        TimeUnit.SECONDS.sleep(3);
        new Thread(()->{
            synchronized (o){
                try {
                    System.out.println(Thread.currentThread().getName() + "加锁成功");
                    o.notify();
//                    o.notifyAll();
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + "执行完毕");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        TimeUnit.MINUTES.sleep(3);

    }

    @Test
    public void testSync() throws InterruptedException {
        Object o = new Object();
        List<Thread> tList = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(() -> {
                synchronized (o) {
                    System.out.println(Thread.currentThread().getName() + "加锁成功");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(Thread.currentThread() + "exception");
                    }
                    System.out.println(Thread.currentThread().getName() + "执行完毕");
                }
            });
            t.setName(i + "");
            t.start();
            tList.add(t);
        }
        TimeUnit.SECONDS.sleep(2);
        tList.get(2).interrupt();
        TimeUnit.SECONDS.sleep(80);

    }
}
