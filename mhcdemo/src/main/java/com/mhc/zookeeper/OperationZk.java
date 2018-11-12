package com.mhc.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.testng.annotations.Test;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 普通操作zk的方法
 */
public class OperationZk {


    public static void main(String[] args) throws Exception {

        //使用原生操作
        CRUDZookeeperToJava();
        System.out.println("原生API操作");
        //使用zkClient操作
        CRUDZookeeperToZkCli();
        System.out.println("ZKClient操作");
        //使用curator操作
        CRUDZookeeperToCurator();
        System.out.println("Curator操作");

    }

    @Test
    private static void CRUDZookeeperToCurator() throws Exception {
        //创建客户端
        String url = "114.116.67.84:2181";

        CuratorFramework curatorFramework = CuratorFrameworkFactory.
                newClient(url, 5000, 5000, new ExponentialBackoffRetry(1000,3));
        curatorFramework.start();
        //增加
        String result = curatorFramework.create().creatingParentsIfNeeded().forPath("/cruator", "is a cruator".getBytes());
        System.out.println("--------> create " + result);
        //修改
        Stat stat = curatorFramework.setData().forPath("/cruator", "update a curator".getBytes());
        System.out.println("---------> update" + stat);
        //查询
        byte[] bytes = curatorFramework.getData().forPath("/cruator");
        System.out.println("---------> find" + new String(bytes));
        //删除
        curatorFramework.delete().forPath("/cruator");

    }

    @Test
    private static void CRUDZookeeperToZkCli() {
        //创建客户端
        String url = "114.116.67.84:2181";
        ZkClient zkClient = new ZkClient(url,2000);
        //增加
        String result = zkClient.create("/aaa", "zkClient".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        System.out.println("------------> crete" + result);
        //修改
        zkClient.writeData("/aaa","zklient update");
        System.out.println("------------> update " );
        //查询
        Object data = zkClient.readData("/aaa");
        System.out.println("------------> read " + data );
        //删除
        boolean b = zkClient.delete("/aaa");
        System.out.println("------------> delete " + b );

    }

    @Test
    private static void CRUDZookeeperToJava() throws Exception {
        //创建客户端
        String url = "114.116.67.84:2181";
        String password = "IOT@POC$2018";
        Integer timeout = 40000;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper(url,timeout,(watchedEvent)->{
            if(watchedEvent.getState()==Watcher.Event.KeeperState.SyncConnected) {
                if (Watcher.Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
                    countDownLatch.countDown();
                }
            }
            System.out.println("Watcher--------> " + watchedEvent.getType());
        });


        countDownLatch.await();
        ZooKeeper.States state = zooKeeper.getState();
        System.out.println("zookeeper state: " + state);


        //增加操作
        zooKeeper.exists("/mhc", true);
        String result = zooKeeper.create("/mhc", "is my first zNode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("----------------> create" + result);
        TimeUnit.SECONDS.sleep(5);
        //查询操作
        byte[] data = zooKeeper.getData("/mhc", true, null);
        System.out.println("----------------> find" + new String(data));
        TimeUnit.SECONDS.sleep(5);
        //修改操作
        Stat stat = zooKeeper.setData("/mhc", "data is update".getBytes(), -1);
        System.out.println("----------------> update" + stat);
        TimeUnit.SECONDS.sleep(5);
        //删除操作
        zooKeeper.exists("/mhc", true);
        zooKeeper.delete("/mhc",-1);
        System.out.println("----------------> delete");
        TimeUnit.SECONDS.sleep(5);

    }

}
