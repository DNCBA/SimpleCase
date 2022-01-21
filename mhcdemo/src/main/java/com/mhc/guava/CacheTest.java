package com.mhc.guava;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cache.guava.GuavaCache;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-01-29 13:21
 */
public class CacheTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheTest.class);


    @Test
    public void loadingCache() throws InterruptedException, ExecutionException {
        MDC.put("traceId","abc");
        LoadingCache<Object, Object> loadingCache = CacheBuilder.from("expireAfterWrite=1m,maximumSize=1000")
                .build(new CacheLoader<Object, Object>() {
                    @Override
                    public Object load(Object o) throws Exception {
                        TimeUnit.SECONDS.sleep(10);
                        LOGGER.info("load data");
                        throw new IllegalStateException();
//                        return "testCache";
                    }
                });


        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                try {
                    LOGGER.info("get data");
                    Object result =  loadingCache.get("aaa");
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            t.setName(i + "");
            t.start();
        }

        TimeUnit.SECONDS.sleep(20);

        LOGGER.info("get after 20 ");
        loadingCache.get("aaa");

    }

    @Test
    public void  cache() throws Exception {
        Cache<String, Object> cache = CacheBuilder
                .newBuilder().maximumSize(1000).expireAfterWrite(30, TimeUnit.SECONDS).build();


        cache.put("aaa","bbb");
        LOGGER.info("add data: {}, {}", "aaa", "bbb");
        while (true) {
            TimeUnit.SECONDS.sleep(15);
            Object aaa = cache.get("aaa",() -> "ccc");
            LOGGER.info("get data: {}, {}", "aaa", aaa);
            aaa = cache.get("bbb", ()->"ddd");
            LOGGER.info("get data: {}, {}", "bbb", aaa);
        }

    }




}
