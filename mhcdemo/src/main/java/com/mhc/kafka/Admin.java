package com.mhc.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class Admin {


    static final String TOPIC_NAME = "testTopic";
    static final Integer PARTATION_NUMBER = 1;
    static final Short REPLACE_NUMBER = 1;

    public static void main(String[] args) {
        try {
            //准备配置
            Properties config = new Properties();
            config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.9.125:9092");
            config.put(AdminClientConfig.CLIENT_ID_CONFIG,"adminClient1");

            //创建客户端
            AdminClient adminClient = AdminClient.create(config);

            //操作 topic
            Set<String> topics = adminClient.listTopics().names().get();
            System.out.println("kafka topic list : " + topics);
            //adminClient.createTopics(Arrays.asList(new NewTopic[]{new NewTopic(TOPIC_NAME,PARTATION_NUMBER,REPLACE_NUMBER)}));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
