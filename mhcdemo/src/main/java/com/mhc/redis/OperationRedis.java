package com.mhc.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.*;

import java.util.Arrays;
import java.util.HashMap;

public class OperationRedis {

    private static String host = "127.0.0.1";
    private static String url = "redis://localhost:6379";


    public static void main(String[] args) {
        try {
//            testJedis();
//            testSpringDataRedis();
            testLettuce();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void testLettuce() {
//        cluster();


        standonly();


    }

    private static void standonly() {
        RedisClient redisClient = RedisClient.create(url);
        StatefulRedisConnection<String, String> connection = redisClient.connect();


        RedisCommands<String, String> redisCommands = connection.sync();

//        redisCommands.auth("a");


        redisCommands.set("standonly","standonly");


        System.out.println("Connected to Redis");

        connection.close();
        redisClient.shutdown();
    }

    private static void cluster() {
        RedisClusterClient redisClient = RedisClusterClient.create(url);

        StatefulRedisClusterConnection<String, String> connection = redisClient.connect();
        RedisAdvancedClusterCommands<String, String> clusterCommands = connection.sync();

        clusterCommands.set("cluster", "cluster");

        System.out.println("Connected to Redis");

        connection.close();
        redisClient.shutdown();
    }


    private static void testSpringDataRedis() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate template = context.getBean(RedisTemplate.class);

        // 设置不同的 key 序列化方式会生成不同的 key 需要在项目中进行注意
        template.setKeySerializer(new StringRedisSerializer());
        // 使用 json 的 value 序列化方式，在反序列化的时候回返回一个 map 不能将对象进行强转
        //template.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        template.setValueSerializer(new JdkSerializationRedisSerializer());


        User user = new User();
        user.setName("zs");
        user.setAge(25);


        template.opsForValue().set("redisTestKey",user);

        User result = (User) template.opsForValue().get("redisTestKey");

        System.out.println(result);



    }

    private static void testJedis() throws Exception {

        //直连
        Jedis jedis = new Jedis(host, 6379);
        jedisOperation(jedis);
        Pipeline pipelined = jedis.pipelined();
        pipelined.set("gxc","gxc");
        String gxc = new Jedis(host,6379).get("gxc");
        System.out.println(gxc);
        pipelined.sync();

        //链接池
        JedisPool jedisPool = new JedisPool(host);
        jedis = jedisPool.getResource();
        jedisOperation(jedis);

        //
        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(new GenericObjectPoolConfig(),
                Arrays.asList( new JedisShardInfo[]{new JedisShardInfo(host)} ));
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        jedisOperation(shardedJedis);


    }

    private static void jedisOperation(JedisCommands jedis) {
        String result;//jedis.auth("");
        result = jedis.set("aa", "bbb");
        result = jedis.get("aa");
        System.out.println(result);
        result = String.valueOf(jedis.del("aa"));
        System.out.println(result);
    }


}
