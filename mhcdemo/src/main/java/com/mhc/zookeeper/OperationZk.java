package com.mhc.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OperationZk {


    public static void main(String[] args) throws Exception {

        //使用原生操作
        CRUDZookeeper();



    }

    @Test
    private static void CRUDZookeeper() throws Exception {
        //创建客户端
        String url = "114.116.67.84:2181";
        String password = "IOT@POC$2018";
        Integer timeout = 40000;
        ZooKeeper zooKeeper = new ZooKeeper(url,timeout,(watchedEvent)->{
            Watcher.Event.EventType type = watchedEvent.getType();
            System.out.println("Event TYPE :"+type);
        });

        ZooKeeper.States state = zooKeeper.getState();
        System.out.println("zookeeper state: " + state);

        TimeUnit.SECONDS.sleep(5);
        //增加操作
        String result = zooKeeper.create("/mhc", "is my first zNode".getBytes(), null, CreateMode.PERSISTENT);
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
