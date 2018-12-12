package com.mhc.ssh;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class SshConnection {

    static String host = "localhost";
    static String userName = "root";
    static String password = "*****";


    public static void main(String[] args) {
        //使用java链接客户端，并执行命令
        testSshConnection();
    }

    private static void testSshConnection() {


        //执行单条语句
        executeSimpleCMD();

        //执行多条语句
        executeBatchCMD();
    }

    private static void executeSimpleCMD() {
        Session session = null;
        Connection connection = null;
        try {
            connection = new Connection(host,22);
            connection.connect();
            if(connection.authenticateWithPassword(userName,password)){
                session = connection.openSession();
                System.out.println("登陆成功");
            }else {
                System.out.println("登陆失败");
                return;
            }

            session.execCommand("ps -ef | grep java ");

            //获取控制台输出
            InputStream inputStream = session.getStdout();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (null != (line = bufferedReader.readLine())){
                System.out.println(line);
            }

            //等待命令执行完成
            session.waitForCondition(ChannelCondition.EXIT_STATUS,1200);
            Integer status = session.getExitStatus();
            System.out.println(status);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null != connection){
                connection.close();
            }
            if(null != session){
                session.close();
            }
        }

    }

    private static void executeBatchCMD() {
        Connection connection = null;
        Session session = null;
        try {
            connection = new Connection(host, 22);
            connection.connect();
            if (connection.authenticateWithPassword(userName,password)){
                session = connection.openSession();
                System.out.println("登陆成功");
            }else {
                System.out.println("登陆失败");
                return;
            }

            session.requestPTY("bash");
            session.startShell();
            PrintWriter writer = new PrintWriter(session.getStdin());
            writer.println("cd /opt/mhc/zookeeper");
            writer.println("cd ../");
            writer.println("ls");
            writer.println("exit");
            writer.close();
            session.waitForCondition(ChannelCondition.CLOSED | ChannelCondition.EOF | ChannelCondition.EXIT_STATUS , 600000);


            BufferedReader reader = new BufferedReader(new InputStreamReader(session.getStdout()));
            String line = "";
            while (null != (line = reader.readLine())){
                System.out.println(line);
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null != connection){
                connection.close();
            }
            if(null != session){
                session.close();
            }

        }
    }
}
