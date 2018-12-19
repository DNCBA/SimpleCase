package com.mhc.mybatis.application;

import com.mhc.mybatis.framework.MySqlSession;
import com.mhc.mybatis.framework.MySqlSessionFactory;
import com.mhc.mybatis.framework.MySqlSessionFactoryBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

public class BootStrap {

    public static void main(String[] args) throws FileNotFoundException {

        URL resource = BootStrap.class.getClassLoader().getResource("");

        FileInputStream fileInputStream = new FileInputStream(resource.getFile());

        MySqlSessionFactory sqlSessionFactory = new MySqlSessionFactoryBuilder().Build(fileInputStream);

        MySqlSession session = sqlSessionFactory.openSession();

        UserMapper mapper = session.getMapper(UserMapper.class);

        User user = mapper.selectById(1);

        System.out.println(user);

    }
}
