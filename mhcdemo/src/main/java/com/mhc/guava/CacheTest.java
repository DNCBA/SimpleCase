package com.mhc.guava;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.guava.GuavaCache;

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


    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        LoadingCache<Object, Object> loadingCache = CacheBuilder.newBuilder()
//                .expireAfterWrite(10, TimeUnit.SECONDS)
//                .build(new CacheLoader<Object, Object>() {
//                    @Override
//                    public Object load(Object o) throws Exception {
//                        System.out.println("load");
//                        return "testCache";
//                    }
//                });


        LoadingCache<Object, Object> loadingCache = CacheBuilder.from("expireAfterWrite=1m,maximumSize=1000")
                .build(new CacheLoader<Object, Object>() {
            @Override
            public Object load(Object o) throws Exception {
                System.out.println("load" + LocalDateTime.now().toString());
                return "testCache";
            }
        });


        while (true) {
            Object aaa = loadingCache.get("aaa");
            System.out.println(aaa + LocalDateTime.now().toString());
            TimeUnit.SECONDS.sleep(60);
        }


    }
}
