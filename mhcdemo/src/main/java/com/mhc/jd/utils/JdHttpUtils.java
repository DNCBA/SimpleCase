package com.mhc.jd.utils;

import io.jsonwebtoken.lang.Collections;
import lombok.Data;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class JdHttpUtils implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(JdHttpUtils.class);

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final Integer CONNECT_TIME_OUT = 2;
    private static final Integer READ_TIME_OUT = 10;
    private static final Integer WRITE_TIME_OUT = 10;
    private static MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");

    private OkHttpClient defaultHttpClient;
    private OkHttpClient unRedirecthttpClient;
    private List<Cookie> cookies = new ArrayList<>();

    public JdHttpUtils(String defaultCookie) {
        if (StringUtils.isNotEmpty(defaultCookie)) {
            for (String cookieString : defaultCookie.split(";")) {
                String[] cookieEntry = cookieString.split("=");
                if (cookieEntry.length >= 2) {
                    Cookie cookie = new Cookie.Builder().name(cookieEntry[0].trim()).value(cookieEntry[1].trim()).domain("jd.com").build();
                    cookies.add(cookie);
                    LOGGER.debug("construct JdHttpUtils init cookie: {}", cookie);
                }
            }
        }
        defaultHttpClient = new OkHttpClient.Builder().connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                        LOGGER.debug("saveFromResponse url: {}, cookes: {}", httpUrl.toString(), list);
                        cookies.addAll(list);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        return cookies;
                    }
                })
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .build();
        unRedirecthttpClient = new OkHttpClient.Builder().connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                        LOGGER.debug("saveFromResponse url: {}, cookes: {}", httpUrl.toString(), list);
                        cookies.addAll(list);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                        return cookies;
                    }
                })
                .followSslRedirects(false)
                .followRedirects(false)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    public JdHttpUtils fork() {
        JdHttpUtils jdHttpUtils = new JdHttpUtils("");
        jdHttpUtils.setCookies(cookies);
        return jdHttpUtils;
    }


    private void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    private Response doExecute(String url, String method, String body, Map<String, String> headerMap, Boolean redirect) {
        StopWatch stopWatch = StopWatch.create();
        stopWatch.start();
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
            LOGGER.debug("HttpUtils doExecute start rep:{}", request);
            if (redirect) {
                response = defaultHttpClient.newCall(request).execute();
            } else {
                response = unRedirecthttpClient.newCall(request).execute();
            }
        } catch (Exception e) {
            LOGGER.error("HttpUtils execute error {} rep:{}", stopWatch.getTime(TimeUnit.MILLISECONDS), request, e);
        }

        LOGGER.debug("HttpUtils doExecute end {} - rep:{} resp:{}", stopWatch.getTime(TimeUnit.MILLISECONDS), request, response);
        return response;
    }


    private HttpUtilsResponse executeString(String url, String method, String body, Map<String, String> headerMap, Boolean redirect) {
        Response response = doExecute(url, method, body, headerMap, redirect);
        HttpUtilsResponse result = new HttpUtilsResponse();
        try {
            if (null != response) {
                result.setCode(response.code());
                result.setStringBody(response.body().string());
                result.setHeads(response.headers());
            }
        } catch (Exception e) {
            LOGGER.error("HttpUtils executeString error url:{}, method:{}", url, method, e);
        }
        return result;
    }

    private HttpUtilsResponse executeBytes(String url, String method, String body, Map<String, String> headerMap) {
        Response response = doExecute(url, method, body, headerMap, true);
        HttpUtilsResponse result = new HttpUtilsResponse();
        try {
            if (null != response) {
                result.setCode(response.code());
                result.setBytesBody(response.body().bytes());
                result.setHeads(response.headers());
            }
        } catch (Exception e) {
            LOGGER.error("exception when executeBytes", e);
        }
        return result;
    }

    public HttpUtilsResponse get(String url, Map<String, String> headerMap) {
        return get(url, headerMap, true);
    }

    public HttpUtilsResponse get(String url, Map<String, String> headerMap, Boolean redirect) {
        return executeString(url, GET, null, headerMap, redirect);
    }


    public HttpUtilsResponse getByBytes(String url
            , Map<String, String> headerMap) {
        return executeBytes(url, GET, null, headerMap);
    }

    public HttpUtilsResponse post(String url, String body
            , Map<String, String> headerMap) {
        return post(url, body, headerMap, true);
    }

    public HttpUtilsResponse post(String url, String body
            , Map<String, String> headerMap, Boolean redirect) {
        return executeString(url, POST, body, headerMap, redirect);
    }


    public String findCookie(String name) {
        if (Collections.isEmpty(cookies)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.name().trim().toLowerCase(), name.trim().toLowerCase())) {
                return cookie.value();
            }
        }
        return null;
    }


    @Data
    public static class
    HttpUtilsResponse {
        Integer code;
        String StringBody;
        byte[] bytesBody;
        Headers heads;
    }
}
