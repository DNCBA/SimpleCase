package com.mhc.tiyan.framewark;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Task {


    @Getter
    @Setter
    protected String name;

    //触发时间
    @Getter
    protected Long time;


    //触发次数
    protected  AtomicLong count;

    //触发逻辑
    protected abstract void run();

    //减少次数逻辑
    public void incrCount() {
        count.decrementAndGet();
    }

    //获取当前次数
    public Long getCount() {
        return count.get();
    }
}
