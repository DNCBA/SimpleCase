package com.mhc.jd;

import com.mhc.jd.config.JdConfig;
import com.mhc.jd.service.JdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class App {


    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        Executor executors = new ThreadPoolExecutor(8, 16, 100, TimeUnit.SECONDS, new SynchronousQueue<>());

        //初始化登录信息
        JdService baseJdService = new JdService(new JdConfig());
        baseJdService.login();

        baseJdService.test();

//        seckill(executors, baseJdService);
    }

    private static void seckill(Executor executors, JdService baseJdService) {
        //测试抢购方法
        executors.execute(() -> {
            JdService forkJdService = baseJdService.fork();
            forkJdService.login();
            LOGGER.info("fork baseJdService seckillBtn success ");
            forkJdService.seckillBtn("100012043978");
        });

        executors.execute(() -> {
            JdService forkJdService = baseJdService.fork();
            forkJdService.login();
            LOGGER.info("fork baseJdService seckillCheckout success");
            forkJdService.seckillCheckout("100012043978");
        });

        executors.execute(() -> {
            JdService forkJdService = baseJdService.fork();
            forkJdService.login();
            LOGGER.info("fork baseJdService seckillBtn seckillSubmit ");
            forkJdService.seckillSubmit("100012043978");
        });


        //并发抢购
        for (int i = 0; i < 5; i++) {
            executors.execute(() -> {
                JdService forkJdService = baseJdService.fork();
                forkJdService.login();
                LOGGER.info("fork baseJdService success ");
                forkJdService.seckill("100012043978");
            });
        }
    }
}
