package com.mhc.spring.framework.context;

import com.mhc.spring.framework.annotation.spring.MHCController;
import com.mhc.spring.framework.annotation.spring.MHCServices;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class MHCContext {

    private Map<String,Object> context = new ConcurrentHashMap<String,Object>();
    private Properties config ;
    private List<String> cacheClss = new ArrayList<String>();
    private String configLocation;

    public MHCContext(String configLocation) {
        this.configLocation = configLocation;

        //focus
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(configLocation);


        //load
        this.config = new Properties();
        try {
            this.config.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //registry
        String path = (String) config.get("scanPackage");
        doRegistry(path);

        //create
        doCreate();

        //populate
        doPopulate();

    }

    private void doPopulate() {

    }

    private void doCreate() {
       if ( cacheClss.size() < 0){
           return;
       }
        String key = null;
        Object value = null;
       try {
           for (String className : cacheClss){
               Class<?> clazz = Class.forName(className.replace(".class", ""));
               if (clazz.isAnnotationPresent(MHCController.class)){
                   MHCController mhcController = clazz.getAnnotation(MHCController.class);
                   key = mhcController.value();
                   value = clazz.newInstance();
               }else if (clazz.isAnnotationPresent(MHCServices.class)){
                   MHCServices mhcServices = clazz.getAnnotation(MHCServices.class);
                   key = mhcServices.value();
                   value = clazz.newInstance();
               }else {
                   continue;
               }
               context.put(toFirstLowerChar(key),value);
           }
       }catch (Exception e){
           e.printStackTrace();
       }

    }

    private String toFirstLowerChar(String key) {
        return null;
    }

    private void doRegistry(String path) {
        File file = new File("/" + path.replaceAll(".", "/"));
        File[] files = file.listFiles();
        for (File f : files){
            if (f.isDirectory()){
                this.doRegistry(path+"."+f.getName());
            }else {
                String className = path + "." + f.getName();
                cacheClss.add(className.replace(".class",""));
            }
        }
    }

    public Object getBean(String name){
        return context.get(name);
    }

    public Map<String, Object> getAll() {
        return null;
    }
}
