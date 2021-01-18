package com.mhc.rpc.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2021-01-14 15:35
 */
public class MyHandler extends SimpleChannelInboundHandler<HashMap<String, Object>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyHandler.class);

    private ApplicationContext context;


    public MyHandler(ApplicationContext context) {
        this.context = context;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HashMap<String, Object> map) throws Exception {
        LOGGER.info("myhandler start {}", JSON.toJSONString(map));
        String methodName = map.get("method").toString();
        String className = map.get("className").toString();
        ((JSONArray) map.get("args")).toArray();
        Object[] args = ((JSONArray) map.get("args")).toArray();
        JSONArray argsTypesArray = (JSONArray) map.get("argsType");
        Class<?>[] argsTypes = new Class<?>[argsTypesArray.size()];

        for (int i = 0; i < argsTypesArray.size(); i++) {
            Object type = argsTypesArray.get(i);
            argsTypes[i] = Class.forName((String) type);
        }
        Class<?> clazz = Class.forName(className);
        Object bean = context.getBean(clazz);
        Method method = clazz.getMethod(methodName, argsTypes);
        Object result = method.invoke(bean, args);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result",result);
        channelHandlerContext.writeAndFlush(resultMap);
        LOGGER.info("myhandler end {},{}", JSON.toJSONString(map), JSON.toJSON(resultMap));
    }
}
