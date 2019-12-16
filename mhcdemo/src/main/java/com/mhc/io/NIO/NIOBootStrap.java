package com.mhc.io.NIO;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NIOBootStrap {

    public static void main(String[] args) throws InterruptedException {

        Integer port = 8801;

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue(5));


        threadPoolExecutor.submit(()->{
            new NIOServer(port).listen().accept();
        });


        TimeUnit.SECONDS.sleep(3);


        threadPoolExecutor.submit(()->{
            new NIOClient(port).start().send();
        });




        TimeUnit.SECONDS.sleep(500);

    }
}
