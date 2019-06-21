package com.mhc.base;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

public class TransformHandler<T> {


    //数据库实体对应的 bean
    private Class<T> clazz;
    //数据库实体对应的 repository
    private Repository repository;
    //实体和表单字段之间的对应关系   表单字段名 - 实体字段名
    private Map<String,String> transformAdapters = new HashMap<>();



    public TransformHandler(Class<T> clazz, Repository repository) {
        this.clazz = clazz;
        this.repository = repository;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public <T> T getInstance() {
        T t = null;
        try {
            t = (T) BeanUtils.instantiateClass(clazz);
        }catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public Map<String, String> getTransformAdapters() {
        return transformAdapters;
    }

    public void setTransformAdapters(Map<String, String> transformAdapters) {
        this.transformAdapters = transformAdapters;
    }

    public TransformHandler addTransformAdapters(String column ,String field) {
        if (StringUtils.isNotBlank(column) && StringUtils.isNotBlank(field)){
            transformAdapters.put(column,clazz.getSimpleName() +":"+field);
        }
        return this;
    }
}
