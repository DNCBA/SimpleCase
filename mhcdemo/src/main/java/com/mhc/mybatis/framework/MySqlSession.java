package com.mhc.mybatis.framework;

import java.lang.reflect.Proxy;

public class MySqlSession {

    private MyConfiguration configuration;
    private MyExecuter myExecuter;


    public MySqlSession(MyConfiguration config, MyExecuter executer) {
        this.configuration=config;
        this.myExecuter=executer;
    }

    public <T> T getMapper(Class<T> t) {

       return (T) Proxy.newProxyInstance(t.getClassLoader(),new Class[]{t},new MyInvokecationHandler(this,t));

    }

    public <T> T selectOne(String sql, Object arg) {
        return myExecuter.query(sql,arg);
    }
}
