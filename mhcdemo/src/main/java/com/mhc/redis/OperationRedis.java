package com.mhc.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
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

    private static String host = "192.168.16.48";

    public static void main(String[] args) {
        try {
            //testJedis();
            testSpringDataRedis();
        }catch (Exception e){
            e.printStackTrace();
        }

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
        Jedis jedis = new Jedis(host);
        jedisOperation(jedis);
        Pipeline pipelined = jedis.pipelined();
        pipelined.set("gxc","gxc");
        String gxc = new Jedis(host).get("gxc");
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
        result = String.valueOf(jedis.del("aa"));
    }


}
