package com.mhc.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.security.PublicKey;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class KafkaTest {





    public static void main(String[] args) {

        try {
            String topicName = "TestTopic";
            Integer partationNumber = 10;

            String message = "message send to Kafka!";
            Integer providerCount = 10;

            Integer consumerCount = 10;

            AdminClient adminClient = getAdminClient();

            // 删除老的 topic
            deleteTopic(topicName,adminClient);
            //创建 topic
            createTopic(topicName,partationNumber,adminClient);
            //创建 10 个 provider
            createProvider(providerCount,message);
            //创建 10 个 comsumer 但是属于同一组
            createConsumer(consumerCount,message);

            TimeUnit.HOURS.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void createConsumer(Integer consumerCount, String message) {
        for (int i=0;i<consumerCount;i++){
            Thread thread = new Thread(() -> {
                reciveMessage();
            });
            thread.setName("consumer"+i);
            thread.start();
        }
    }

    private static void reciveMessage() {

        Properties config = new Properties();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        config.put(ConsumerConfig.GROUP_ID_CONFIG,"consumer1");
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
        config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"100");



        KafkaConsumer consumer = new KafkaConsumer(config);
        consumer.subscribe(Arrays.asList("TestTopic"));

        while (true) {
            ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMinutes(10));
            for (ConsumerRecord<String, String> record : consumerRecords) {
                System.out.println(Thread.currentThread().getName() + "<---------- recive data :" + record.value());
            }
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createProvider(Integer providerCount, String message) {
        for (int i=0;i<providerCount;i++){
            Thread thread = new Thread(() -> {
                sendMessage(message);
            });
            thread.setName("provider"+i);
            thread.start();
        }
    }

    private static void sendMessage(String message) {
        try {
            Map<String, Object> configs = new HashMap<>();
            configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
            configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            configs.put(ProducerConfig.ACKS_CONFIG, "all");
            configs.put(ProducerConfig.CLIENT_ID_CONFIG,Thread.currentThread().getName());
            configs.put(ProducerConfig.BATCH_SIZE_CONFIG,"1");
            configs.put(ProducerConfig.LINGER_MS_CONFIG,"1");
            configs.put(ProducerConfig.BUFFER_MEMORY_CONFIG,"10240");
            configs.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,"20000");
            configs.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG,"30000");
            KafkaProducer producer = new KafkaProducer(configs);
            for (int i=0;i<1;i++) {
                String topic = "TestTopic";
                producer.send(new ProducerRecord(topic,message));
                System.out.println(Thread.currentThread().getName() + "------>send message : "+ message + ",to TOPIC ： " + topic );
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteTopic(String topicName,AdminClient adminClient ) {
        adminClient.deleteTopics(Arrays.asList(topicName));
        System.out.println("----------------------->" + "topic 删除完成");
    }

    private static AdminClient getAdminClient() {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        config.put(AdminClientConfig.CLIENT_ID_CONFIG,"adminClient1");
        AdminClient adminClient = AdminClient.create(config);
        return adminClient;
    }

    private static void createTopic(String topicName, Integer partationNumber,AdminClient adminClient) throws ExecutionException, InterruptedException {

        adminClient.createTopics(Arrays.asList(new NewTopic(topicName, partationNumber, (short) 1)));
        Set<String> strings = adminClient.listTopics().names().get();
        Collection<ConsumerGroupListing> consumerGroupListings = adminClient.listConsumerGroups().all().get();
        System.out.println("topicNames:"+strings);
        System.out.println("consumerGroup:"+consumerGroupListings);
        System.out.println("----------------------->" + "topic 创建完成");
    }

}
