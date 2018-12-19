package com.mhc.mybatis.framework;

import java.io.FileInputStream;
import java.util.Properties;

public class MySqlSessionFactoryBuilder {

    //根据传递进来的配置文件，生成sqlSessionFactory
    public MySqlSessionFactory Build(FileInputStream fileInputStream) {

        try {
            Properties properties = new Properties();
            properties.load(fileInputStream);

            MyConfiguration configuration = new MyConfiguration(properties);
            return new MySqlSessionFactory(configuration);
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }
}
