package com.mhc.http.httpclient;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2019-08-19 13:22
 */
public class OkHttpTest {

  public static void main(String[] args) throws IOException {

    OkHttpClient httpClient = new OkHttpClient();
    Builder builder = httpClient.newBuilder();

    builder = builder.connectTimeout(1000, TimeUnit.MILLISECONDS)
        .readTimeout(1000, TimeUnit.MILLISECONDS)
        .writeTimeout(1000, TimeUnit.MILLISECONDS);

    OkHttpClient okHttpClient = builder.build();

    Request.Builder requestBuilder = new Request.Builder();
    Request request = requestBuilder.url("http://www.eol.cn/html/zhongxue/shzxx/index.shtml").method("GET",null).build();

    Response response = okHttpClient.newCall(request).execute();

    String string = response.body().toString();

    System.out.println(string);




  }

}
