package com.mhc.nacos;

import java.util.Properties;
import java.util.concurrent.Executor;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.config.NacosConfigService;

/**
 * Config service example
 *
 * @author Nacos
 */
public class NacosConfig {

    public static void main(String[] args) throws NacosException, InterruptedException {
        String serverAddr = "localhost";
        String dataId = "1";
        String group = "STG";
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
//        ConfigService configService = NacosFactory.createConfigService(properties);
        NacosConfigService configService = new NacosConfigService(properties);

//        configService.addListener(dataId, group, new Listener() {
//            @Override
//            public Executor getExecutor() {
//                return null;
//            }
//
//            @Override
//            public void receiveConfigInfo(String configInfo) {
//                System.out.println("Listener:" + configInfo);
//            }
//        });

        String content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);


        boolean isPublishOk = configService.publishConfig(dataId, group, "content");
        System.out.println(isPublishOk);

        for (int i = 0; i < 20; i++) {
            Thread.sleep(3000);
            content = configService.getConfig(dataId, group, 5000);
            System.out.println("getConfig:" + content);
        }
    }
}

