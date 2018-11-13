package com.mhc.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimpleMessageQueue {


    private static final String mRoot = "/messageQueue";
    private static final String path = mRoot+"/"+"message";
    private static final String url = "114.116.67.84:2181";

    public static void main(String[] args) throws InterruptedException {
        //测试基于zk的分布式消息队列
        testMessageQueue();
    }

    private static void testMessageQueue() throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        //start five provider
        //start five consumer
        for (int i = 0 ;i <5;i++){
            executorService.submit(()->{
                try {
                    putMessage();
                } catch (Exception e) {
                    System.out.println("----------------->" + "message send error");
                }
            });
        }
        TimeUnit.SECONDS.sleep(10);
        for (int i = 0 ;i <5;i++){
            executorService.submit(()->{
                try {
                    getMessage();
                } catch (Exception e) {
                    System.out.println("----------------->" + "message get error");
                    e.printStackTrace();
                }
            });
        }





    }

    private static void getMessage() throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.newClient(url, new ExponentialBackoffRetry(1000, 3));
        client.start();
        while (true) {
            if (client.getChildren().forPath(mRoot).size() <= 0) {
                continue;
            }

            try {
                String result = client.getChildren().forPath(mRoot).get(0);
                String msgPath = mRoot + "/" + result;
                String data = new String(client.getData().forPath(msgPath));
                client.delete().forPath(msgPath);
                System.out.println(Thread.currentThread().getName() + ":消费者 get message -------> " + data);
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + "<------- 消费失败");
            }
        }
    }

    private static void putMessage() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(url).retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        client.start();

        String msg = UUID.randomUUID().toString().replaceAll("-","");
        client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(path,msg.getBytes());
        System.out.println(Thread.currentThread().getName() + ":生产者 send message -------> " + msg);
    }

}
