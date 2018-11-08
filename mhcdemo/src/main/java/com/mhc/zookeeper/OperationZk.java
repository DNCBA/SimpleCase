package com.mhc.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class OperationZk {


    public static void main(String[] args) throws Exception {

        //使用原生操作
        CRUDZookeeperToJava();

        //使用zkClient操作
        CRUDZookeeperToZkCli();

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
                    System.out.println(watchedEvent.getState() + "-->" + watchedEvent.getType());
                }
            }
        });


        countDownLatch.await();
        ZooKeeper.States state = zooKeeper.getState();
        System.out.println("zookeeper state: " + state);


        //增加操作
        String result = zooKeeper.create("/mhc", "is my first zNode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("----------------> create" + result);
        TimeUnit.SECONDS.sleep(5);
        //修改操作
        Stat stat = zooKeeper.setData("/mhc", "data is update".getBytes(), -1);
        System.out.println("----------------> update" + stat);
        TimeUnit.SECONDS.sleep(5);
        //查询操作
        byte[] data = zooKeeper.getData("/mhc", null, null);
        System.out.println("----------------> find" + new String(data));
        TimeUnit.SECONDS.sleep(5);
        //删除操作
        zooKeeper.delete("/mhc",-1);
        System.out.println("----------------> delete");
        TimeUnit.SECONDS.sleep(5);

    }

}
