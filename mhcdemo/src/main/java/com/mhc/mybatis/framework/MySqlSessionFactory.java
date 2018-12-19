package com.mhc.mybatis.framework;

import java.util.Properties;

public class MySqlSessionFactory {

    private MyConfiguration config;

    public MySqlSessionFactory(MyConfiguration configuration) {
        this.config = configuration;
    }

    public MySqlSession openSession() {
        return new MySqlSession(config,config.newExecuter());
    }
}
