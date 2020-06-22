package com.mhc.base;

import org.mortbay.util.ajax.JSON;

import javax.annotation.PostConstruct;

public class Demo {
    private Integer id;
    private String name;


    @PostConstruct
    public void init(){
        id = 1;
        name = "tome";
    }


    @Override
    public String toString() {
        return "Demo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static void main(String[] args) {
        Demo demo = new Demo();
        System.out.println(JSON.toString(demo));
    }
}
