package com.mhc.ssh;

public class SshConnection {

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

    private static void executeBatchCMD() {

    }

    private static void executeSimpleCMD() {

    }
}
