package com.mhc.redis;

import org.apache.kafka.common.protocol.types.Field;

import java.io.Serializable;

public class User implements Serializable {


    private static final long serialVersionUID = -3431107412952680959L;
    private String name;
    private String address;
    private Integer age;
    private String level;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", level='" + level + '\'' +
                '}';
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
