package com.mhc.io.NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    private Integer port;
    private Selector selector;
    private ByteBuffer buffer;


    public NIOServer(Integer port) {
        try {
            this.port = port;
            this.selector = Selector.open();
            this.buffer = ByteBuffer.allocate(1024);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public NIOServer listen(){
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(this.port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);

            System.out.println("NIO服务器已经启动了");


        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public void accept() {

        try {
            while (true){

                int size = selector.select();
                if (size == 0){
                    continue;
                }

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();

                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isAcceptable()){
                        ServerSocketChannel serverSocketChannelchannel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = serverSocketChannelchannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(this.selector,SelectionKey.OP_READ);
                    }

                    if (key.isReadable()){
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        socketChannel.configureBlocking(false);
                        int len = socketChannel.read(this.buffer);
                        this.buffer.flip();
                        System.out.println("NIO服务器接收到数据："+ new String(this.buffer.array()));
                        this.buffer.clear();
                        socketChannel.register(this.selector,SelectionKey.OP_WRITE);
                    }

                    if (key.isWritable()){
                        SocketChannel socketChannel  = (SocketChannel) key.channel();
                        socketChannel.configureBlocking(false);
                        ByteBuffer response = ByteBuffer.wrap("hello this is NIO Server".getBytes());
                        socketChannel.write(response);
                        socketChannel.close();
                    }
                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

}
