package com.mhc.mybatis.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.mhc.mybatis.demo.mapper")
public class MybatisConfig {


    @Bean
    public DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/LXK?serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }


    @Bean
    public SqlSessionFactory getSqlSessionFactory() {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, getDataSource());
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment);
        //注册拦截器
        configuration.addInterceptor(new QueryInterceptor());
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory;
    }




}
