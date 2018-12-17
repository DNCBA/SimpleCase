package com.mhc.mybatis.framework;

public class MySqlSession {


    public <T> T getMapper(Class<T> t) {

        return (T) t;
    }
}
