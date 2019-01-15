package com.mhc.activeMQ.queue;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class TestQueue {


    private static final String host = "tcp://localhost:61616";
    private static final String queueName = "mhcQueue";

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(4,8,60, TimeUnit.SECONDS,new ArrayBlockingQueue<>(5));
        try {
            executorService.submit(TestQueue::sendMsg);
            executorService.submit(TestQueue::reciveMsg);
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
            Queue queue = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(queue);
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
            Queue queue = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener((message -> {
                System.out.println(message);
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
