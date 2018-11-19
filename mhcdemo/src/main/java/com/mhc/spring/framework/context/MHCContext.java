package com.mhc.spring.framework.context;

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

    }

    private void doRegistry(String path) {
        File file = new File("/" + path.replaceAll(".", "/"));


    }

    public Object getBean(String name){
        return context.get(name);
    }

    public Map<String, Object> getAll() {
        return null;
    }
}
