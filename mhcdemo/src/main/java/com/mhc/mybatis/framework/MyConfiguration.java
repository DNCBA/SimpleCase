package com.mhc.mybatis.framework;

import java.util.Properties;

public class MyConfiguration {

    private Properties config;

    public MyConfiguration(Properties properties) {
        this.config = properties;
    }

    public Properties getConfig() {
        return config;
    }

    public MyExecuter newExecuter() {
        return new SimpleMyExecuter(this);
    }
}
