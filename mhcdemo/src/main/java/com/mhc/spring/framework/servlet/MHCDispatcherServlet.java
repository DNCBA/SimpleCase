package com.mhc.spring.framework.servlet;


import com.mhc.spring.framework.annotation.spring.MHCController;
import com.mhc.spring.framework.annotation.springMVC.MHCRequestMapping;
import com.mhc.spring.framework.annotation.springMVC.MHCRequestParm;
import com.mhc.spring.framework.context.MHCContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class MHCDispatcherServlet extends HttpServlet {

    private Map<String,Handler> handlerMappings = new HashMap<String,Handler>();
    private Map<Handler,HandlerAdapter> handlerAdapters = new HashMap<Handler,HandlerAdapter>();




    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);

        //根据请求的url进行映射

        //执行对应的方法

        //根据结果进行渲染

    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        String parameter = config.getInitParameter("contextConfigLocation");
        //初始化context容器
        MHCContext context = new MHCContext(parameter);

        //all init method

        //init multipartResolver
        initMultipartResolver(context);
        //init localResolver
        initLocaleResolver(context);
        //init themeResolver
        initThemeResolver(context);
        //init handlerMapping
        initHandlerMappings(context);
        //init handlerAdapter
        initHandlerAdapters(context);
        //init handlerExceptionResolver
        initHandlerExceptionResolvers(context);
        //init translator
        initRequestToViewNameTranslator(context);
        //init viewResolvers
        initViewResolvers(context);
        //init flashManager
        initFlashMapManager(context);



    }

    private void initMultipartResolver(MHCContext context) {
        System.out.println("---> init multipartResolver");
    }
    private void initLocaleResolver(MHCContext context) {
        System.out.println("---> init localResolver");
    }
    private void initThemeResolver(MHCContext context) {
        System.out.println("--> init ThemeResovler");
    }


    private void initHandlerMappings(MHCContext context) {
        //scan context
        Map<String,Object> ioc = context.getAll();
        //create url and handler
        for (Map.Entry entry : ioc.entrySet()){
            Object controller = entry.getValue();
            if (!controller.getClass().isAnnotationPresent(MHCController.class)){
                continue;
            }
            MHCRequestMapping classRequestMapping = controller.getClass().getAnnotation(MHCRequestMapping.class);
            String classURL = classRequestMapping.value();
            Method[] methods = controller.getClass().getMethods();
            for (Method method : methods){
                if (method.isAnnotationPresent(MHCRequestMapping.class)){
                    MHCRequestMapping methodRequestMapping = method.getAnnotation(MHCRequestMapping.class);
                    String methodURL = methodRequestMapping.value();
                    String urlKey = "/"+classURL + "/" + methodURL;
                    urlKey = urlKey.replaceAll("/+", "/");
                    handlerMappings.put(urlKey,new Handler(controller,method));
                }
            }
        }
    }

    private void initHandlerAdapters(MHCContext context) {
        Map<String,Integer> parameters = null;
        if (handlerMappings.isEmpty()){
            return;
        }
        for (Map.Entry entry : handlerMappings.entrySet()){
            parameters = new HashMap<String,Integer>();
            Handler handler = (Handler) entry.getValue();
            Method method = handler.getMethod();
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0 ; i < parameterTypes.length ; i++){
                Class<?> type = parameterTypes[i];
                if (type == HttpServletRequest.class || type == HttpServletResponse.class){
                    parameters.put(type.getSimpleName(),i);
                }
            }
            Annotation[][] annotations = method.getParameterAnnotations();
            for (int i = 0 ; i < annotations.length ; i++){
                if (annotations[i].length <= 0){
                    return;
                }
                if (annotations[i][0] instanceof MHCRequestParm){
                    parameters.put(((MHCRequestParm)annotations[i][0]).value(),i);
                }
            }
            HandlerAdapter handlerAdapter = new HandlerAdapter(parameters);
            handlerAdapters.put(handler,handlerAdapter);
        }
    }

    private void initHandlerExceptionResolvers(MHCContext context) {}
    private void initRequestToViewNameTranslator(MHCContext context) {}
    private void initViewResolvers(MHCContext context) {}
    private void initFlashMapManager(MHCContext context) {}


    private class Handler {
        private Object target;
        private Method method;

        public Handler(Object target, Method method) {
            this.target = target;
            this.method = method;
        }

        public Object getTarget() {
            return target;
        }

        public Method getMethod() {
            return method;
        }
    }

    private class HandlerAdapter {
       private Map<String,Integer> paramters;
       private Object[] parameterValues;

        public HandlerAdapter(Map<String, Integer> paramters) {
            this.paramters = paramters;
        }
    }
}
