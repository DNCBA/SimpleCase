package com.mhc.io.AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AIOServer {

    private Integer port;

    public AIOServer(Integer port) {
        this.port = port;
    }

    public void listener() {

        try {

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(this.port));
            System.out.println("AIO服务已经启动");

            serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    buffer.clear();
                    result.read(buffer);
                    buffer.flip();
                    System.out.println(new String(buffer.array()));


                }

                @Override
                public void failed(Throwable exc, Object attachment) {

                }
            });


            TimeUnit.SECONDS.sleep(500);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
