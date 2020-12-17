package com.mhc.utils;

import lombok.Data;
import okhttp3.*;
import okhttp3.OkHttpClient.Builder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-08-19 20:44
 */
public class HttpUtils {

  private static final String GET = "GET";
  private static final String POST = "POST";
  private static final String DELETE = "DELETE";
  private static final String PUT = "PUT";

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);


  protected static OkHttpClient httpClient;
  protected static MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");


  static {
    Builder builder = new Builder();
    builder.connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS);
    httpClient = builder.build();
  }


  protected static Optional<Response> doExecute(String url, String method, String body, Map<String, String> headerMap) {
    StopWatch stopWatch = StopWatch.create();
    Request.Builder requestBuilder = new Request.Builder();
    RequestBody requestBody = null;
    Headers headers = null;
    Request request = null;
    Response response = null;
    try {
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
      LOGGER.info("HttpUtils doExecute start rep:{}", request);
      response = httpClient.newCall(request).execute();
    } catch (Exception e) {
      LOGGER.error("HttpUtils execute error {} rep:{}", stopWatch.getTime(TimeUnit.MILLISECONDS), request, e);
    }

    LOGGER.info("HttpUtils doExecute end {} - rep:{} resp:{}", stopWatch.getTime(TimeUnit.MILLISECONDS), request, response);
    return Optional.ofNullable(response);
  }


  private static Optional<HttpUtilsResponse> executeString(String url, String method, String body, Map<String, String> headerMap) {
    Optional<Response> optional = doExecute(url, method, body, headerMap);
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

  private static Optional<HttpUtilsResponse> executeBytes(String url, String method, String body, Map<String, String> headerMap) {
    Optional<Response> optional = doExecute(url, method, body, headerMap);
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
    return get(url, null);
  }

  public static Optional<HttpUtilsResponse> get(String url, Map<String, String> headerMap) {
    return executeString(url, GET, null, headerMap);
  }

  public static Optional<HttpUtilsResponse> post(String url, String body) {
    return post(url, body, null);
  }

  public static Optional<HttpUtilsResponse> post(String url, String body,
      Map<String, String> headerMap) {
    return executeString(url, POST, body, headerMap);
  }

  public static Optional<HttpUtilsResponse> delete(String url, String body) {
    return delete(url, body, null);
  }

  public static Optional<HttpUtilsResponse> delete(String url, String body,
      Map<String, String> headerMap) {
    return executeString(url, DELETE, body, headerMap);
  }

  public static Optional<HttpUtilsResponse> put(String url, String body) {
    return put(url, body, null);
  }

  public static Optional<HttpUtilsResponse> put(String url, String body, Map<String, String> headerMap) {
    return executeString(url, PUT, body, headerMap);
  }

  @Data
  public static class
  HttpUtilsResponse {
    Integer code;
    String StringBody;
    byte[] bytesBody;
  }


}
