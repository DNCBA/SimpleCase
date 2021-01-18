package com.mhc.rpc.register;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-01-14 14:00
 */
public class RpcRegister {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcRegister.class);
    private static HashMap<String, List<Config>> serviceConfig = new HashMap<>();


    public static void register(String serviceName, String host, Integer port) {
        Config config = new Config();
        config.setHost(host);
        config.setPort(port);
        List<Config> configList = serviceConfig.getOrDefault(serviceName, new ArrayList<Config>());
        configList.add(config);
        serviceConfig.put(serviceName, configList);
        LOGGER.info("service {} register {}:{}", serviceName, host, port);
    }


    public static List<Config> findService(String serviceName) {
        return serviceConfig.get(serviceName);
    }


    @Data
    static class Config {
        private String host;
        private Integer port;
    }
}
