package com.mhc.activeMQ.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestTopic {
    private static final String host = "tcp://localhost:61616";
    private static final String topicName = "mhcTopic";

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(4,8,60, TimeUnit.SECONDS,new ArrayBlockingQueue<>(5));
        try {
            executorService.submit(TestTopic::sendMsg);
            executorService.submit(TestTopic::reciveMsg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void sendMsg() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(host);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(topicName);
            MessageProducer producer = session.createProducer(topic);
            for (int i = 0; i < 5; i++) {
                String message = "hello ActiveMQ" + UUID.randomUUID().toString();
                System.out.println("send message:" + message);
                producer.send(session.createTextMessage(message));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reciveMsg() {
        try {

            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,host);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(topicName);
            MessageConsumer consumer = session.createConsumer(topic);
            consumer.setMessageListener((message -> {
                System.out.println(message);
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
