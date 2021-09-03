package com.mhc;


import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils {

    private static final Random random = new Random();
    private static final Pattern WLFSTK_SMDL_PATTERN = Pattern.compile("(?<=wlfstk_smdl=).*?(?=;)");

    public static final String COOKIE = "cookie";
    public static final String SET_COOKIE = "set-cookie";
    public static final String LOGIN_ASPX = "https://passport.jd.com/new/login.aspx";
    public static final String LOGIN_QR_SHOW = "https://qr.m.jd.com/show?appid=133&size=147&t=";
    public static final String LOGIN_QR_CHECK = "https://qr.m.jd.com/check?callback=%s&appid=133&token=%s&_=%s";

    public static String getToken(String cookie) {
        Matcher matcher = WLFSTK_SMDL_PATTERN.matcher(cookie);
        matcher.find();
        return matcher.group();
    }

    public static String getJQuery() {
        return "jQuery" + (random.nextInt(9999999) % (9999999 - 1000000 + 1) + 1000000);
    }

    public static String getTime() {
        return "" + System.currentTimeMillis();
    }
}
