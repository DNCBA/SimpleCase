package com.mhc.socket;


import org.testng.annotations.Test;

import java.io.*;
import java.net.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * tcp通讯和udp通讯的样例代码
 */
public class Transaction {


    private ExecutorService  executorService = Executors.newCachedThreadPool();

    @Test
    public void test(){
        //tcp链接
        testTCP();
        //udp链接
        //testUDP();
    }

    private void testUDP() {
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
