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


import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class KafkaOpertaion {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        topic();

        //send();

        //recive();


    }

    private static void topic() throws InterruptedException, ExecutionException {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.0.185:9092");
        config.put(AdminClientConfig.CLIENT_ID_CONFIG,"adminClient1");
        AdminClient adminClient = AdminClient.create(config);
        adminClient.createTopics(Arrays.asList(new NewTopic("topic1", 4, (short) 1)));
        adminClient.deleteTopics(Arrays.asList("topic1"));
        Set<String> topics = adminClient.listTopics().names().get();
        System.out.println("-------------->"+topics);
    }

    private static void recive() {
        try {
            Properties config = new Properties();
            config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"114.116.67.84:9092");
            config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
            config.put(ConsumerConfig.GROUP_ID_CONFIG,"consumer1");
            config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
            config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"100");



            KafkaConsumer consumer = new KafkaConsumer(config);
            consumer.subscribe(Arrays.asList("test"));

            while (true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(10));
                for (ConsumerRecord<String, String> record : consumerRecords) {
                    System.out.println("recive data key: " + record.key() + ",value:" + record.value());
                }
                TimeUnit.SECONDS.sleep(10);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void send() {
        try {
            Map<String, Object> configs = new HashMap<>();
            configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"114.116.67.84:9092");
            configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            configs.put(ProducerConfig.ACKS_CONFIG, "all");
            configs.put(ProducerConfig.CLIENT_ID_CONFIG,"productor1");
            configs.put(ProducerConfig.BATCH_SIZE_CONFIG,"1");
            configs.put(ProducerConfig.LINGER_MS_CONFIG,"1");
            configs.put(ProducerConfig.BUFFER_MEMORY_CONFIG,"10240");
            configs.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,"20000");
            configs.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG,"30000");
            KafkaProducer producer = new KafkaProducer(configs);
            for (int i=0;i<10;i++) {
                String result = UUID.randomUUID().toString();
                String topic = "test";
                producer.send(new ProducerRecord(topic,result));
                System.out.println("send message : "+ result + ",to TOPIC ： " + topic );
                TimeUnit.SECONDS.sleep(2);
            }
            producer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
