package com.mhc.proxy.jdk;


import com.mhc.proxy.FunctionImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Function;
import java.util.function.Supplier;

public class JdkProxy {

    public static void main(String[] args) {
        Supplier supplier = (Supplier) Proxy.newProxyInstance(FunctionImpl.class.getClassLoader(), FunctionImpl.class.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object invoke = method.invoke(new FunctionImpl(), args);
                return invoke + "result";
            }
        });
        Object result = supplier.get();
        System.out.println("Proxy:" + result );
    }
}
