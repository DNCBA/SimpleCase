package com.mhc.redis;

import org.apache.kafka.common.protocol.types.Field;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class User implements Serializable {


    public static void main(String[] args) {
        Random random = new Random();
        int i = random.nextInt(50);
        System.out.println(i);

        List<Double> collect = Stream.generate(Math::random).map(aDouble -> aDouble * 10000L).limit(4).collect(Collectors.toList());
        System.out.println(collect);

    }


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
