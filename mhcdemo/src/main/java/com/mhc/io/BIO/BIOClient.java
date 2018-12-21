package com.mhc.io.BIO;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BIOClient {

    private Integer port;
    private InetAddress inetAddress;

    public BIOClient(Integer port, InetAddress inetAddress) {
        this.port = port;
        this.inetAddress = inetAddress;
    }

    public  void send(){
        try {
            Socket socket = new Socket(this.inetAddress,this.port);


            OutputStream outputStream = socket.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write("hello BIO" + Thread.currentThread().getName());
            bufferedWriter.flush();

            outputStream.close();
            socket.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
