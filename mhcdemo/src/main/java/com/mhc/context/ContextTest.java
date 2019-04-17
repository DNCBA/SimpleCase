package com.mhc.context;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class ContextTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(SpringConfiguration.class);
        applicationContext.refresh();
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        redisTemplate.opsForValue().set("TextContextKey","good");
    }
}
