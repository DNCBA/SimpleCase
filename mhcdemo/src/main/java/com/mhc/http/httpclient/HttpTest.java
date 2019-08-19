package com.mhc.http.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class HttpTest {


    private static final String URL = "http://www.eol.cn/html/zhongxue/shzxx/index.shtml";

    public static void main(String[] args) {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        testGetRequest(httpClient);

//        testPutRequest(httpClient);
//
//        testDeleteRequest(httpClient);
//
//        testPostRequest(httpClient);


    }

    private static void testPostRequest(CloseableHttpClient httpClient) {
        HttpPost httpPost = new HttpPost(URL);
        doEntity(httpClient,httpPost);

    }

    private static void testDeleteRequest(CloseableHttpClient httpClient) {
        HttpDelete httpDelete = new HttpDelete(URL);
        doParams(httpClient,httpDelete);

    }

    private static void testPutRequest(CloseableHttpClient httpClient) {
        HttpPut httpPut = new HttpPut(URL);
        doEntity(httpClient,httpPut);
    }

    private static void testGetRequest(CloseableHttpClient httpClient) {
        HttpGet httpGet = new HttpGet(URL);
        doParams(httpClient, httpGet);
    }

    private static void doParams(CloseableHttpClient httpClient, HttpUriRequest httpUriRequest) {
        try {
//            HttpParams params = httpClient.getParams();
//            params.setParameter("key","");
//            httpUriRequest.setParams(params);
            CloseableHttpResponse response = httpClient.execute(httpUriRequest);
            String line = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).readLine();
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doEntity(CloseableHttpClient httpClient, HttpEntityEnclosingRequestBase HttpRequest) {
        try {
            StringEntity stringEntity = new StringEntity("{}", APPLICATION_JSON);
            HttpRequest.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(HttpRequest);
            String line = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).readLine();
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
