package com.mhc.utils;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
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

  public static final String GET = "GET";
  public static final String POST = "POST";
  public static final String DELETE = "DELETE";
  public static final String PUT = "PUT";

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);


  protected static OkHttpClient httpClient;
  protected static MediaType MEDIA_TYPE_JSON = MediaType.get("application/json");


  static {
    Builder builder = new Builder();
    builder.connectTimeout(100000, TimeUnit.MICROSECONDS)
        .readTimeout(100000, TimeUnit.MICROSECONDS)
        .writeTimeout(100000, TimeUnit.MICROSECONDS);
    httpClient = builder.build();
  }


  private static Optional<Response> doExecute(String url, String method, String body,
      Map<String, String> headerMap) {
    LOGGER.info("HttpUtils execute url:{},method:{},body:{},headers:{}", url, method, body,
        headerMap);
    Request.Builder requestBuilder = new Request.Builder();
    RequestBody requestBody = null;
    Headers headers = null;
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
      Request request = builder.build();
      response = httpClient.newCall(request).execute();
    } catch (Exception e) {
      LOGGER.error("HttpUtils execute error url:{},method:{}", url, method, e);
    }
    return Optional.ofNullable(response);
  }


  private static Optional<HttpUtilsResponse> executeString(String url, String method, String body,
      Map<String, String> headerMap) {
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

  private static Optional<HttpUtilsResponse> executeBytes(String url, String method, String body,
      Map<String, String> headerMap) {
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

  public static Optional<HttpUtilsResponse> put(String url, String body,
      Map<String, String> headerMap) {
    return executeString(url, PUT, body, headerMap);
  }

  static class HttpUtilsResponse {

    Integer code;
    String StringBody;
    byte[] bytesBody;

    public Integer getCode() {
      return code;
    }

    public void setCode(Integer code) {
      this.code = code;
    }

    public String getStringBody() {
      return StringBody;
    }

    public void setStringBody(String stringBody) {
      StringBody = stringBody;
    }

    public byte[] getBytesBody() {
      return bytesBody;
    }

    public void setBytesBody(byte[] bytesBody) {
      this.bytesBody = bytesBody;
    }
  }


}
