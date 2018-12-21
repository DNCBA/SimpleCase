package com.mhc.io.NIO;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClient {

    private Selector selector;
    private Integer port;
    private ByteBuffer buffer;
    private SocketChannel socketChannel;

    public NIOClient( Integer port) {
        try {
            this.selector = Selector.open();
            this.port = port;
            this.buffer = ByteBuffer.allocate(1024);
        }catch (Exception e){

        }

    }

    public NIOClient start(){
        try {
            this.socketChannel = SocketChannel.open(new InetSocketAddress(InetAddress.getLocalHost(),8089));
            socketChannel.configureBlocking(false);
            socketChannel.register(this.selector,SelectionKey.OP_READ);
            System.out.println("NIO客户端启动成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public void send(){

        try {


            socketChannel.write(ByteBuffer.wrap("hello ".getBytes()));


            while (true){
                int size = selector.select();
                if (size == 0){
                    continue;
                }

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isReadable()){
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        socketChannel.configureBlocking(false);
                        socketChannel.read(this.buffer);
                        this.buffer.flip();
                        System.out.println("接收到服务端响应："+new String(this.buffer.array()));
                        socketChannel.close();
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
