package com.mhc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ProxyProviderUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyProviderUtils.class);

    /**
     * 讯代理 获取代理地址信息
     */
    private static final String PROXY_URL = "";

    private ProxyProviderUtils() {
    }

    private static LoadingCache<String, List<String>> cachePool = CacheBuilder.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(10))
            .build(new CacheLoader<String, List<String>>() {
                @Override
                public List<String> load(String key) throws Exception {
                    List<String> result = new ArrayList();
                    for (; ; ) {
                        if (Objects.equals("proxy", key)) {
                            LOGGER.info("cache load proxy");
                            Optional<HttpUtils.HttpUtilsResponse> optional = HttpUtils.get(PROXY_URL);
                            optional.ifPresent(resp -> {
                                String stringBody = resp.getStringBody();
                                JSONObject jsonObject = JSON.parseObject(stringBody);
                                JSONArray jsonArray = jsonObject.getJSONArray("RESULT");
                                for (Object o : jsonArray) {
                                    JSONObject proxyObject = (JSONObject) o;
                                    LOGGER.debug("add proxyString {}", o.toString());
                                    String ip = proxyObject.getString("ip");
                                    String port = proxyObject.getString("port");
                                    result.add(ip + ":" + port);
                                }
                            });
                        }
                        if (!result.isEmpty()) {
                            break;
                        }
                    }
                    return result;
                }
            });

    public static Proxy RandomProxy() throws ExecutionException {
        List<String> proxyList = cachePool.get("proxy");
        Integer index = select("random", proxyList.size());
        return buildProxy(proxyList, index);
    }

    public static Integer select(String random, Integer maxSize) {
        int result;
        if (StringUtils.isBlank(random)) {
            random = "random";
        }
        switch (random) {
            case "random":
            default:
                Random rand = new Random();
                result = rand.nextInt(maxSize);
                break;
        }
        return result;
    }

    private static Proxy buildProxy(List<String> proxyList, Integer index) {
        String proxyString = proxyList.get(index);
        LOGGER.info("buildProxy {} {}", proxyString, index);
        String[] strings = proxyString.split(":");
        String ip = strings[0];
        int port = Integer.parseInt(strings[1]);
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
    }
}
