package com.mhc.serialize;

import com.alibaba.fastjson.JSON;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.caucho.hessian.io.HessianOutput;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhc.jdbc.User;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.annotations.TestInstance;

import java.io.*;
import java.util.Base64;
import java.util.UUID;

/**
 * 各种序列化的相关测试
 */
public class Serialize {

    private User user = null;

    {
        User u = new User();
        u.setId(1);
        u.setName("zs");
        u.setAge(18);
        this.user=u;
    }

    @Test
    public void test() throws Exception {

        //jdk自带的序列化
        testSerialized();
        //base64
        testBase64();
        //jackson序列化
        testJackson();
        //fastjson序列化
        testFastjson();
        //json序列化
        testJson();
        //hessian序列化
        testHessian();
        //protobuf序列化
        testProtobuf();

    }

    @Test
    public void testBase64() {
        String code = UUID.randomUUID().toString();
        System.out.println("原始数据大小:"+code.getBytes().length+"原始数据:"+code);
        String encodeData = Base64.getEncoder().encodeToString(code.getBytes());
        System.out.println("编码后大小:"+ encodeData.getBytes().length+ "编码后数据"+encodeData);
        byte[] decode = Base64.getDecoder().decode(encodeData);
        System.out.println("解码后数据大小:"+decode.length+"解码后数据:"+new String(decode));
    }


    @Test
    private void testProtobuf() throws IOException {

        //注意被序列化的对象需要加上对应的注解

        Codec<User> codec = ProtobufProxy.create(User.class);
        long begain = System.currentTimeMillis();
        byte[] bytes = codec.encode(user);
        long end = System.currentTimeMillis();
        System.out.println("protobuff:"+bytes.length+"-------------->  time:"+(end-begain));

        //反序列化
        //User user = codec.decode(bytes);
    }

    @Test
    private void testHessian() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HessianOutput hessianOutput = new HessianOutput(outputStream);
        long begain = System.currentTimeMillis();
        hessianOutput.writeObject(user);
        long end = System.currentTimeMillis();
        System.out.println("hession:"+outputStream.toByteArray().length+"-------------->  time:"+(end-begain));

        //反序列化
        //Object object = new HessianInput(new ByteArrayInputStream(outputStream.toByteArray())).readObject();
    }

    @Test
    private void testFastjson() {
        long begain = System.currentTimeMillis();
        byte[] bytes = JSON.toJSONBytes(user);
        long end = System.currentTimeMillis();
        System.out.println("fastjson:"+bytes.length+"-------------->  time:"+(end-begain));

        //反序列化
        //Object object = JSON.parse(bytes);
    }

    @Test
    private void testJson() {
        long begain = System.currentTimeMillis();
        JSONObject jsonObject = new JSONObject(user);
        String result = jsonObject.toString();
        long end = System.currentTimeMillis();
        System.out.println("json:"+result.getBytes().length+"-------------->  time:"+(end-begain));

        //JSONObject jsonObject1 = new JSONObject(result);
    }

    @Test
    private void testJackson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        long begain = System.currentTimeMillis();
        byte[] bytes = mapper.writeValueAsBytes(user);
        long end = System.currentTimeMillis();
        System.out.println("jackson:"+bytes.length+"-------------->  time:"+(end-begain));

        //反序列化
        //User user = mapper.readValue(bytes, User.class);
    }

    @Test
    private void testSerialized() throws IOException{
       //注意：序列化对象需要实现Serizliable接口
       //      序列化对象不序列化静态变量
       //      序列化对象的父类是否序列化在于父类是否实现序列化接口

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        long begain = System.currentTimeMillis();
        objectOutputStream.writeObject(user);
        long end = System.currentTimeMillis();
        System.out.println("jdk:"+outputStream.toByteArray().length+"-------------->  time:"+(end-begain));

        //反序列化
        //Object object = new ObjectInputStream(new ByteArrayInputStream(outputStream.toByteArray())).readObject();

    }
}
