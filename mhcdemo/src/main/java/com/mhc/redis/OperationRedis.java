package com.mhc.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.*;

import java.util.Arrays;

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
        template.opsForValue().set("wyl","wyl");
        Object wyl = template.opsForValue().get("wyl");
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
