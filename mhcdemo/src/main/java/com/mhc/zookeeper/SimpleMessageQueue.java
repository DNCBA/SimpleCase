package com.mhc.zookeeper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleMessageQueue {

    public static void main(String[] args) {
        //测试基于zk的分布式消息队列
        testMessageQueue();
    }

    private static void testMessageQueue() {

        ExecutorService executorService = Executors.newCachedThreadPool();

        //start five provider
        //start five consumer
        for (int i = 0 ;i <5;i++){
            executorService.submit(()->{
                putMessage();
                getMessage();
            });
        }




    }

    private static void getMessage() {
        //TODO
    }

    private static void putMessage() {
        //TODO
    }
}
