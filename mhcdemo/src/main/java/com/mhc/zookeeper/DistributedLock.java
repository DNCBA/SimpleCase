package com.mhc.zookeeper;



import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;


import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * 分布式锁的两种zk实现
 */
public class DistributedLock {

    private static final String url = "114.116.67.84:2181";
    private static final Integer sessionTimeOut = 5000;
    private static final Integer connectionTimeOut = 5000;
    private static final String eRoot = "/eRoot";
    private static final String pRoot = "/pRoot";

    public static void main(String[] args) throws Exception {

        //互斥锁实现
        testMutesLock();

        //共享锁实现
        testSharedLock();

    }

    private static void testSharedLock() {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0 ; i < 10 ; i ++){
            executorService.submit(()->{
                try {
                    sharedLock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void testMutesLock() throws Exception {

        ExecutorService service = Executors.newFixedThreadPool(3);


        for (int i = 0; i < 10; i++) {
            service.submit(() -> {
                try {
                    mutesLock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }


    }

    public static void mutesLock() throws Exception {

        MutesClient serviceClient = new MutesClient();

        serviceClient.tryLock();


    }

    private static void sharedLock() throws Exception {
        SharedClient sharedClient = new SharedClient();

        sharedClient.tryLock();
    }

    protected static class MutesClient{

        private String clientID = UUID.randomUUID().toString().replaceAll("-","");
        private String clientName = "save";
        private String path = eRoot + "/lock" + clientName;
        private CuratorFramework client = CuratorFrameworkFactory.newClient(url, sessionTimeOut, connectionTimeOut,
                new ExponentialBackoffRetry(1000, 3));
        public volatile Boolean isLocking = false;
        private Boolean flag = false;

        public String getLocking() {
            try {
                return new String(client.getData().forPath(path));
            } catch (Exception e) {
                return null;
            }
        }

        public String getClientID() {
            return clientID;
        }

        public String getClientName() {
            return clientName;
        }

        public String getPath() {
            return path;
        }

        public CuratorFramework getClient() {
            if (!flag){
                client.start();
                flag = true;
            }
            return client;
        }

        private  void locking() {
            isLocking = false;
            while ( !isLocking ){
                try {
                    client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).
                            forPath(path,clientID.getBytes());
                    System.out.println(Thread.currentThread().getName()+"线程锁定");
                    TimeUnit.SECONDS.sleep(new Random().nextInt(10));
                    release();
                }catch (Exception e){
                    isLocking = true;
                }
            }
        }

        private  void release() throws Exception {
            if (null != client.checkExists().forPath(path)){
                if (clientID.equals(getLocking())){
                    client.delete().forPath(getPath());
                    System.out.println(Thread.currentThread().getName()+"线程释放");
                }
            }
        }

        private  void tryLock() throws Exception {

            //create Listener
            PathChildrenCache pathChildrenCache = new PathChildrenCache(getClient(),eRoot,true);
            pathChildrenCache.start();
            pathChildrenCache.getListenable().addListener((curatorFramework, pathChildrenCacheEvent) -> {
                if (getPath().equals(pathChildrenCacheEvent.getData().getPath()) && PathChildrenCacheEvent.Type.CHILD_REMOVED.equals(pathChildrenCacheEvent.getType())){
                    //System.out.println(Thread.currentThread().getName()+" Listener : ------> LOCK RELEASE ");
                    locking();
                }
                if (getPath().equals(pathChildrenCacheEvent.getData().getPath()) && PathChildrenCacheEvent.Type.CHILD_ADDED.equals(pathChildrenCacheEvent.getType())){
                    //System.out.println(Thread.currentThread().getName()+" Listener : ------> LOCK LOCKING " + getLocking());
                    isLocking = true;
                }
            });


            if (null == getClient().checkExists().forPath(getPath())){
                locking();
            }
        }
    }

    protected static class SharedClient {

        private String clientID = UUID.randomUUID().toString().replaceAll("-","");
        private String clientName = "update";
        private String path = pRoot + "/lock" + clientName;
        private CuratorFramework client = CuratorFrameworkFactory.newClient(url, sessionTimeOut, connectionTimeOut,
                new ExponentialBackoffRetry(1000, 3));
        public  Boolean isGET = false;
        private Boolean flag = false;


        public void release() throws Exception {
            if (isGET){
                String result = client.getChildren().forPath(pRoot).get(0);
                String id = new String(client.getData().forPath(pRoot +"/"+  result));
                if (clientID.equals(id)){
                    client.delete().forPath(pRoot+"/"+ result);
                    System.out.println(Thread.currentThread().getName()+"线程释放锁" + result);
                }
            }
        }

        public void locking() throws Exception {
            if (!flag) {
                client.start();
                flag=true;
            }
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path,clientID.getBytes());
        }

        public void regest() throws Exception {
            PathChildrenCache pathChildrenCache = new PathChildrenCache(client, pRoot, true);
            pathChildrenCache.start();
            pathChildrenCache.getListenable().addListener((curatorFramework,pathChildrenCacheEvent)->{
                if (PathChildrenCacheEvent.Type.CHILD_REMOVED.equals(pathChildrenCacheEvent.getType()) ||
                        PathChildrenCacheEvent.Type.CHILD_ADDED.equals(pathChildrenCacheEvent.getType())){
                    String result = client.getChildren().forPath(pRoot).get(0);
                    String id = new String(curatorFramework.getData().forPath(pRoot +"/"+  result));
                    if (clientID.equals(id)){
                        isGET = true;
                        System.out.println(Thread.currentThread().getName()+"线程获得锁" + result);
                        TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                        release();
                    }
                }
            });
        }

        public void tryLock() throws Exception {
            locking();
            regest();
        }
    }
}
