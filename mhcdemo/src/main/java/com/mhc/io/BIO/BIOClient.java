package com.mhc.io.BIO;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class BIOClient {

    private Integer port;
    private InetAddress inetAddress;

    public BIOClient(Integer port, InetAddress inetAddress) {
        this.port = port;
        this.inetAddress = inetAddress;
    }

    public void send() {
        try {
            Socket socket = new Socket(this.inetAddress, this.port);


            OutputStream outputStream = socket.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write("hello BIO" + Thread.currentThread().getName());
            bufferedWriter.flush();
            outputStream.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recive(){
        try {
            Socket socket = new Socket(this.inetAddress, this.port);
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            if (null != (line = bufferedReader.readLine())){
                System.out.println(line);
            }
            inputStream.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws UnknownHostException {
        BIOClient bioClient = new BIOClient(8090, InetAddress.getLocalHost());
        bioClient.send();
        bioClient.recive();
    }
}
