package com.mhc.jdbc;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import java.io.Serializable;

public class User  implements Serializable {
    @Protobuf(fieldType= FieldType.INT32,required = false,order = 1)
    private Integer id;
    @Protobuf(fieldType= FieldType.STRING,required = false,order = 2)
    private String name;
    @Protobuf(fieldType= FieldType.INT32,required = false,order = 3)
    private Integer age;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
