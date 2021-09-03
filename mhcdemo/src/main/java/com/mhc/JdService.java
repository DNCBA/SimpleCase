package com.mhc;

import com.mhc.utils.HttpUtils;
import okhttp3.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JdService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdService.class);


    private Map<String, String> reqHeaders;

    //登录流程
    public void login() throws InterruptedException {
        //初始化 cookie
        initCookie();
        //获取页面登录的 cookie
        HttpUtils.get(UrlUtils.LOGIN_ASPX).ifPresent(this::setSession);
        //获取登录的二维码
        reqHeaders.put("referer", "https://passport.jd.com/new/login.aspx");
        HttpUtils.getByBytes(UrlUtils.LOGIN_QR_SHOW, reqHeaders, false).ifPresent(resp -> {
            setSession(resp);
            try {
                File file = new File("qr.png");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(resp.getBytesBody());
                LOGGER.info("generate qr code in: {}", file.getAbsolutePath());
            } catch (Exception e) {
                LOGGER.error("generate qr code error ", e);
            }
        });
        //判断登录状态
        while (true) {
            reqHeaders.put("referer", "https://passport.jd.com/new/login.aspx");
            String url = String.format(UrlUtils.LOGIN_QR_CHECK, UrlUtils.getJQuery(), UrlUtils.getToken(reqHeaders.get(UrlUtils.COOKIE)), UrlUtils.getTime());
            HttpUtils.get(url).ifPresent(resp -> {
                LOGGER.info(resp.getStringBody());
            });
            TimeUnit.SECONDS.sleep(10);
        }
    }

    private HttpUtils.HttpUtilsResponse setSession(HttpUtils.HttpUtilsResponse resp) {
        Headers heads = resp.getHeads();
        String cookie = heads.get(UrlUtils.SET_COOKIE);
        reqHeaders.put(UrlUtils.COOKIE, reqHeaders.get(UrlUtils.COOKIE) + cookie);
        LOGGER.info("setSession: {}", cookie);
        return resp;
    }

    private void initCookie() {
        Map<String, String> defaultHeader = new HashMap<>();
        defaultHeader.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        defaultHeader.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36");
        defaultHeader.put("cookie", "");
        this.reqHeaders = defaultHeader;
    }


    public static void main(String[] args) throws InterruptedException {
        JdService jdService = new JdService();
        jdService.login();

    }
}
