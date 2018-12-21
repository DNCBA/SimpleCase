package com.mhc.io.BIO;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BIOBootstrap {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));


        poolExecutor.submit(()->{
            BIOServer bioServer = new BIOServer(8090);
            bioServer.listener();
        });

        TimeUnit.SECONDS.sleep(3);

        poolExecutor.submit(()->{
            try {
                BIOClient bioClient = new BIOClient(8090, InetAddress.getLocalHost());
                bioClient.send();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        });


    }
}
