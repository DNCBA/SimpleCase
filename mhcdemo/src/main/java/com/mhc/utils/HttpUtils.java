package com.mhc.utils;

import lombok.Data;
import okhttp3.*;
import okhttp3.OkHttpClient.Builder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Proxy;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class HttpUtils {

  private static final String GET = "GET";
  private static final String POST = "POST";
  private static final String DELETE = "DELETE";
  private static final String PUT = "PUT";
  private static final Integer CONNECT_TIME_OUT = 2;
  private static final Integer READ_TIME_OUT = 10;
  private static final Integer WRITE_TIME_OUT = 10;

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

  private static OkHttpClient httpClient;
  private static MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");

  static {
    httpClient = new Builder().connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .build();
  }

  private static Optional<Response> doExecute(String url, String method, String body, Map<String, String> headerMap, Boolean proxy) {
    StopWatch stopWatch = StopWatch.create();
    stopWatch.start();
    Request.Builder requestBuilder = new Request.Builder();
    RequestBody requestBody = null;
    Headers headers = null;
    Request request = null;
    Response response = null;
    try {
      OkHttpClient proxyClient =null;
      if (proxy) {
        Proxy proxyConfig = ProxyProviderUtils.RandomProxy();
        proxyClient = httpClient.newBuilder().proxy(proxyConfig).build();
      }
      Request.Builder builder = requestBuilder.url(url);
      if (StringUtils.isNotBlank(body)) {
        requestBody = RequestBody.create(MEDIA_TYPE_JSON, body);
      }
      builder.method(method, requestBody);
      if (null != headerMap && headerMap.size() > 0) {
        headers = Headers.of(headerMap);
        builder.headers(headers);
      }
      request = builder.build();
      LOGGER.debug("HttpUtils doExecute start rep:{}", request);
      if (proxy && null != proxy){
        response = proxyClient.newCall(request).execute();
      }else {
        response = httpClient.newCall(request).execute();
      }
    } catch (Exception e) {
      LOGGER.error("HttpUtils execute error {} rep:{}", stopWatch.getTime(TimeUnit.MILLISECONDS), request, e);
    }

    LOGGER.info("HttpUtils doExecute end {} - rep:{} resp:{}", stopWatch.getTime(TimeUnit.MILLISECONDS), request, response);
    return Optional.ofNullable(response);
  }


  private static Optional<HttpUtilsResponse> executeString(String url, String method, String body, Map<String, String> headerMap, Boolean proxy) {
    Optional<Response> optional = doExecute(url, method, body, headerMap, proxy);
    HttpUtilsResponse result = null;
    try {
      if (optional.isPresent()) {
        Response response = optional.get();
        result = new HttpUtilsResponse();
        result.setCode(response.code());
        result.setStringBody(response.body().string());
      }
    } catch (Exception e) {
      LOGGER.error("HttpUtils executeString error url:{},method:{}", url, method, e);
    }
    return Optional.ofNullable(result);
  }

  private static Optional<HttpUtilsResponse> executeBytes(String url, String method, String body, Map<String, String> headerMap, Boolean proxy) {
    Optional<Response> optional = doExecute(url, method, body, headerMap, proxy);
    HttpUtilsResponse result = new HttpUtilsResponse();
    try {
      if (optional.isPresent()) {
        Response response = optional.get();
        result.setCode(response.code());
        result.setBytesBody(response.body().bytes());
      }
    } catch (Exception e) {
      LOGGER.error("HttpUtils executeByte error url:{},method:{}", url, method, e);
    }
    return Optional.ofNullable(result);
  }


  public static Optional<HttpUtilsResponse> get(String url) {
    return get(url, null, false);
  }

  public static Optional<HttpUtilsResponse> get(String url
          , Map<String, String> headerMap, Boolean proxy) {
    return executeString(url, GET, null, headerMap, proxy);
  }

  public static Optional<HttpUtilsResponse> post(String url, String body) {
    return post(url, body, null, false);
  }

  public static Optional<HttpUtilsResponse> post(String url, String body
          , Map<String, String> headerMap, Boolean proxy) {
    return executeString(url, POST, body, headerMap, proxy);
  }

  public static Optional<HttpUtilsResponse> delete(String url, String body) {
    return delete(url, body, null, false);
  }

  public static Optional<HttpUtilsResponse> delete(String url, String body
          , Map<String, String> headerMap, Boolean proxy) {
    return executeString(url, DELETE, body, headerMap, proxy);
  }

  public static Optional<HttpUtilsResponse> put(String url, String body) {
    return put(url, body, null, false);
  }

  public static Optional<HttpUtilsResponse> put(String url, String body
          , Map<String, String> headerMap, Boolean proxy) {
    return executeString(url, PUT, body, headerMap, proxy);
  }

  @Data
  public static class
  HttpUtilsResponse {
    Integer code;
    String StringBody;
    byte[] bytesBody;
  }


}
