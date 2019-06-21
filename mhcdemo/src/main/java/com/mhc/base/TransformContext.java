package com.mhc.base;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransformContext {

    //表单 - 实体映射关系容器
    private static Map<String, List<TransformHandler>> transformHandlerContext = new HashMap<String,List<TransformHandler>>();

    // 注入方法
    public static Boolean registHandler(String formKey , List<TransformHandler> handlers){
        transformHandlerContext.put(formKey, handlers);
        return true;
    }

    //对外提供的方法
    public static List<TransformHandler> getTHandlers(String key){
        List<TransformHandler> handlers = null;
        if (transformHandlerContext.size() > 0){
            handlers = transformHandlerContext.get(key);
        }
        return handlers;
    }



}
