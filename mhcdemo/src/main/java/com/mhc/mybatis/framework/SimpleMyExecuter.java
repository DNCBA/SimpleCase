package com.mhc.mybatis.framework;

import com.mhc.mybatis.application.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SimpleMyExecuter implements MyExecuter {

    private MyConfiguration configuration;

    public SimpleMyExecuter(MyConfiguration myConfiguration) {
        this.configuration = myConfiguration;
    }

    @Override
    public <E> E query(String sql, Object arg) {
        User user = new User();
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setObject(1,arg);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){


                user.setUserId((Integer) resultSet.getObject(0));
                user.setUsername((String) resultSet.getObject(1));
                user.setPassword((String) resultSet.getObject(2));

            }


        }catch (Exception e){

        }
        return (E) user;
    }

    private Connection getConnection() {

        Connection connection = null;
        try {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获得链接
            connection =  DriverManager.getConnection(configuration.getConfig().getProperty("url"),
                                        configuration.getConfig().getProperty("username"),
                                        configuration.getConfig().getProperty("password"));

        }catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }


}
