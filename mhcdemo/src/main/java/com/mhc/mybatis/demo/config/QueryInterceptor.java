package com.mhc.mybatis.demo.config;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Properties;

@Intercepts(value = {
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})}
)
public class QueryInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryInterceptor.class);


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        LOGGER.info("QueryInterceptor args {}", args);
        Method method = invocation.getMethod();
        LOGGER.info("QueryInterceptor method {}",method.getName());
        Object target = invocation.getTarget();
        LOGGER.info("QueryInterceptor target {}",target.getClass().getName());
        Object resutl = invocation.proceed();
        LOGGER.info("QueryInterceptor result {}", resutl);

        return resutl;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
