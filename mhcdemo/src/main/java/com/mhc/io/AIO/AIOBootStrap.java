package com.mhc.io.AIO;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AIOBootStrap {

    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));

        poolExecutor.submit(()->{
            AIOServer aioServer = new AIOServer(8801);
            aioServer.listener();
        });

        TimeUnit.SECONDS.sleep(3);

        poolExecutor.submit(()->{
            try {
                AIOClient aioClient = new AIOClient(8801, InetAddress.getLocalHost());
                aioClient.send();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        });

        TimeUnit.SECONDS.sleep(500);

    }

}
