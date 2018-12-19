package com.mhc.mybatis.framework;

public interface MyExecuter {
    <E> E query(String sql, Object arg);
}
