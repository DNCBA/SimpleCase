package com.mhc.io.AIO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.TimeUnit;

public class AIOClient {

    private Integer port;
    private InetAddress inetAddress;

    public AIOClient(Integer port, InetAddress inetAddress) {
        this.port = port;
        this.inetAddress = inetAddress;
    }

    public void  send(){
        try {
            AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();

            socketChannel.connect(new InetSocketAddress(this.inetAddress,this.port), null, new CompletionHandler<Void, Object>() {
                @Override
                public void completed(Void result, Object attachment) {
                    System.out.println("AIO客户端发送数据");
                    ByteBuffer data = ByteBuffer.wrap("hello AIO".getBytes());
                    socketChannel.write(data);
                }

                @Override
                public void failed(Throwable exc, Object attachment) {

                }
            });


            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
