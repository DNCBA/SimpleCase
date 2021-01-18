package com.mhc.io.BIO;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class BIOServer {

    private Integer port;
    private ExecutorService executorService;

    public BIOServer(Integer port) {
        this.port = port;
        executorService = new ThreadPoolExecutor(5, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
    }


    public void listener(){

        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(this.port));
            System.out.println("BIO服务器启动了");

            while (true){

                Socket socket = serverSocket.accept();
                executorService.submit(()->{
                   process(socket);
                });
            }


        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void process(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while (null != (line = bufferedReader.readLine())){
                System.out.println("BIO收到信息："+line);
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write("hello is BIO");

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        BIOServer bioServer = new BIOServer(8090);
        bioServer.listener();
    }

}
