package com.mhc.mybatis.framework;

import com.mhc.mybatis.application.UserMapperXML;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvokecationHandler implements InvocationHandler {

    private MySqlSession mySqlSession;
    private Class taraget;


    public <T> MyInvokecationHandler(MySqlSession mySqlSession, Class<T> t) {
        this.mySqlSession = mySqlSession;
        this.taraget=t;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String nameSpeaces = taraget.getName();
        String id = method.getName();

        String sql = "";
        if (nameSpeaces.equals(UserMapperXML.NAMESPEACES)){
            String result = UserMapperXML.METHODMAPPING.get(id);
            if (null != result){
                sql=result;
            }
        }

        Object result = mySqlSession.selectOne(sql, args[0]);

        return result;
    }
}
