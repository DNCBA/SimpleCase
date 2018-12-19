package com.mhc.socket;


import org.testng.annotations.Test;

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * tcp通讯和udp通讯的样例代码
 */
public class Transaction {


    private ExecutorService  executorService = Executors.newCachedThreadPool();


    public static void main(String[] args) {
        Transaction transaction = new Transaction();

        transaction.testBIO();

        transaction.testNIO();


    }

    @Test
    public void testNIO() {

        CountDownLatch latch = new CountDownLatch(2);
        executorService.submit(()->{
            try {
                testNIOService();
                latch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(()->{
            try {
                testNIOClient();
                latch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void testNIOClient() throws IOException {
        InetAddress localHost = InetAddress.getLocalHost();
        Integer port = 8090;
        Socket socket = new Socket(localHost, 8090);

        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

        writer.write("hello NIO");

        writer.flush();

        writer.close();

        socket.close();

        System.out.println("客户端发送数据结束");
    }

    private void testNIOService() throws IOException {


        //打开服务器通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress(8090));
        //创建多路复用器
        Selector selector = Selector.open();
        //将多路复用器注册到服务器中
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("NIO服务器已经启动");

        while (true) {
            //多路复用器开始监听
            selector.select();
            //获得多路复用器已经选择的链接集合
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey selectionKey = keys.next();
                keys.remove();
                if (selectionKey.isValid()) {
                    if (selectionKey.isAcceptable()) {
                        System.out.println("socket is acceptable");
                        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel accept = channel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector,SelectionKey.OP_READ);
                    }
                    if (selectionKey.isReadable()) {
                        System.out.println("socket is readable");
                    }
                    if (selectionKey.isWritable()) {
                        System.out.println("socket is writable");
                    }
                }
            }
        }


    }

    @Test
    public  void testBIO(){
        //tcp链接
        testTCP();
        //udp链接
        testUDP();
    }

    private void  testUDP() {
        //启动udp服务端
        executorService.submit(()->{
            try {
                udpService();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //启动udp客户端
        executorService.submit(()->{
            try {
                udpClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            TimeUnit.SECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void udpClient() throws Exception {
        System.out.println("udpClient启动了");
        //1.创建一个datagramsocket
        DatagramSocket socket = new DatagramSocket(8081);
        //2.创建一个datagrampackage
        byte[] bytes = new byte[1024];
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
        while (true) {
            //3.接受数据
            socket.receive(packet);
            //4.解析数据
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            byte[] data = packet.getData();
            String string = new String(data);
            System.out.println("address:" + address + " port:" + port + " data:" + string);
        }
    }

    private void udpService() throws Exception {
        System.out.println("udpService启动了");
        //1.创建一个datagramsocket
        DatagramSocket datagramSocket = new DatagramSocket();
        //2.创建一个datagrampackage
        InetAddress localHost = InetAddress.getLocalHost();
        byte[] bytes = "hello udp ".getBytes();
        DatagramPacket packet = new DatagramPacket(bytes,bytes.length,localHost,8081);
        //3.循环发送数据
        while (true){
            datagramSocket.send(packet);
            TimeUnit.SECONDS.sleep(5);
        }
    }

    private void testTCP() {
        CountDownLatch latch = new CountDownLatch(2);
        //启动tcp服务端
        executorService.submit(()->{
            try {
                tcpService();
                latch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        //启动tcp客户端
        executorService.submit(()->{
            try {
                tcpClient();
                latch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tcpClient() throws Exception {
        System.out.println("tcpClient启动了");
        //1.创建socket
        InetAddress localHost = InetAddress.getLocalHost();
        Socket socket = new Socket(localHost,8082);
        //2.获得输出流
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        //3.输出数据
        writer.write("hello tcp");
        writer.flush();
        //4.释放资源
        writer.close();
        outputStream.close();
        socket.close();
        System.out.println("tcpClient结束了");
    }

    private void tcpService() throws Exception {
        System.out.println("tcpService启动了");
        //1.创建ServiceSocket监听端口
        ServerSocket serverSocket = new ServerSocket(8082);
        //2.获得socket
        Socket socket = serverSocket.accept();
        //3.获得输入流
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        //4.读取数据
        String line = null;
        while ( null != (line = reader.readLine()) ){
            System.out.println(line);
        }
        System.out.println("tcpClient启动了");
        //5.释放资源
        reader.close();
        socket.close();
        serverSocket.close();
        System.out.println("tcpService结束了");
    }
}
