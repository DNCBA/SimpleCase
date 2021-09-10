package com.mhc.jd.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mhc.jd.config.JdConfig;
import com.mhc.jd.exception.RetryException;
import com.mhc.jd.utils.JdHttpUtils;
import com.mhc.jd.utils.UrlUtils;
import okhttp3.Headers;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JdService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdService.class);

    private static Pattern pattern = Pattern.compile("(?<=<title>).+(?=</title>)");


    private Map<String, String> headers = new HashMap<>();
    private JdHttpUtils jdHttpUtils;
    private JdConfig jdConfig;

    /**
     * 当前用户登录状态 COOKIE(指定 cookie 登录)/QR(二维码登录)/SUCCESS(登录成功)/ERROR(登录错误).
     */
    private String loginStatus = "COOKIE";
    /**
     * 保存当前用户名称.
     */
    private String nickName;
    /**
     * 保存sku商品信息.
     */
    private Map<String, String> itemInfo = new HashMap<String, String>();


    public JdService(JdConfig jdConfig) {
        this.jdConfig = jdConfig;
        MDC.put("traceId", RandomStringUtils.random(8, true, true));
        this.jdHttpUtils = new JdHttpUtils(jdConfig.getProperty("cookie"));
        if (StringUtils.isEmpty(jdConfig.getProperty("cookie"))) {
            loginStatus = "QR";
        }
        headers.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
    }

    /**
     * 登录主方法,支持 cookie 和二维码登录两种方式.
     */
    public void login() {
        MDC.put("mainId", "LOGIN");
        while (true) {
            try {
                if (Objects.equals("QR", loginStatus)) {
                    qrLogin();
                }
                basicInfoLoad();
                break;
            } catch (IllegalArgumentException e) {
                LOGGER.error("exception when login", e);
            }
        }
        loginStatus = "SUCCESS";
    }

    /**
     * 根据 sku 进行商品预约.
     */
    public void reserve(String skuId) {
        MDC.put("mainId", "RESERVE");
        //获取商品信息
        getItemInfo(skuId);
        headers.put("Referer", String.format("https://item.jd.com/%s.html", skuId));

        String reserveUrl = null;
        while (true) {
            JdHttpUtils.HttpUtilsResponse fetchResp = jdHttpUtils.get(String.format(UrlUtils.RESERVE_FETCH, skuId), headers);
            String fetchRespStringBody = fetchResp.getStringBody();
            LOGGER.debug("reserve fetchRespStringBody: {}", fetchRespStringBody);
            JSONObject fetchObject = JSON.parseObject(fetchRespStringBody.replaceAll("fetchJSON\\(", "").replaceAll("\\);", ""));
            if (StringUtils.isNotEmpty(fetchObject.getString("url"))) {
                reserveUrl = "https:" + fetchObject.getString("url");
                LOGGER.info("reserve get reserve url success, {}", reserveUrl);
                break;
            }
        }

        MDC.put("subId", "RESERVE");
        while (true) {
            try {
                JdHttpUtils.HttpUtilsResponse reserveResp = jdHttpUtils.get(reserveUrl, headers);
                LOGGER.debug("reserve reserveRespStringBody:{}", reserveResp.getStringBody());
                if (Objects.equals(200, reserveResp.getCode()) && (reserveResp.getStringBody().contains("您已成功预约过了，无需重复预约")
                        || reserveResp.getStringBody().contains("预约成功，已获得抢购资格"))) {
                    LOGGER.info("reserve success ");
                    break;
                }
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                LOGGER.info("exception when reserve ", e);
            }
        }
    }

    /**
     * 根据 sku 进行秒杀.
     */
    public void seckill(String skuId) {
        MDC.put("mainId", "SECKILL");
        getItemInfo(skuId);
        while (true) {
            try {
                //访问按钮的抢购地址
                seckillBtn(skuId);
                while (true) {
                    //访问抢购订单结算页面
                    seckillCheckout(skuId);
                    //访问提交订单
                    seckillSubmit(skuId);
                }
            } catch (Exception e) {
                LOGGER.error("exception when seckill", e);
            }
        }
    }

    /**
     * 用户登录信息.
     */
    public JdService fork() {
        jdConfig.put("cookie", "1=1");
        JdService jdService = new JdService(jdConfig);
        if (Objects.equals("SUCCESS", loginStatus)) {
            jdService.setJdHttpUtils(jdHttpUtils.fork());
        } else {
            throw new IllegalStateException("未登录状态不可复制");
        }
        return jdService;
    }

    /**
     * 充值 jdHttpUtils.
     */
    private void setJdHttpUtils(JdHttpUtils jdHttpUtils) {
        this.jdHttpUtils = jdHttpUtils;
    }

    /**
     * 登录成功后加载用户基础信息.
     */
    private void basicInfoLoad() {
        MDC.put("subId", "BASICINFOLOAD");
        //校验登录状态
        headers.put("Referer", "<script>window.location.href='https://passport.jd.com/uc/login?ReturnUrl=http%3A%2F%2Forder.jd.com%2Fcenter%2Flist.action'</script>");
//        while (true) {
//            JdHttpUtils.HttpUtilsResponse resp = jdHttpUtils.get(String.format(UrlUtils.LOGIN_STATUS_CHECK, UrlUtils.getTime()), headers);
//            if (Objects.equals(200, resp.getCode()) && resp.getStringBody().contains("<title>我的京东--我的订单</title>")) {
//                LOGGER.debug("login status is success! ");
//                break;
//            }
//        }
        //加载用户信息
        getUserInfo();
    }

    /**
     * 二维码登录.
     */
    private void qrLogin() {
        MDC.put("subId", "QRLOGIN");
        //获取页面登录的 cookie
        jdHttpUtils.get(UrlUtils.LOGIN_ASPX, headers);
        //获取登录二维码和token
        headers.put("Referer", "https://passport.jd.com/new/login.aspx");
        while (true) {
            JdHttpUtils.HttpUtilsResponse qrResp = jdHttpUtils.getByBytes(String.format(UrlUtils.LOGIN_QR_SHOW, UrlUtils.getTime()), headers);
            if (Objects.equals(200, qrResp.getCode())) {
                try {
                    File file = new File("qr.png");
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(qrResp.getBytesBody());
                    LOGGER.info("generate qr code in: {}", file.getAbsolutePath());
                    break;
                } catch (Exception e) {
                    LOGGER.error("generate qr code error ", e);
                }
            }
            LOGGER.warn("generate qr retry");
        }

        //用token换ticket
        String ticket = null;
        headers.put("Referer", "https://passport.jd.com/new/login.aspx");
        while (true) {
            try {
                String url = String.format(UrlUtils.LOGIN_QR_CHECK, UrlUtils.getJQuery(), jdHttpUtils.findCookie("wlfstk_smdl"), UrlUtils.getTime());
                JdHttpUtils.HttpUtilsResponse ticketResp = jdHttpUtils.get(url, headers);
                LOGGER.debug("qrLogin ticketResp: {}", ticketResp.getStringBody());
                JSONObject ticketObject = JSON.parseObject(ticketResp.getStringBody().replaceAll("jQuery.+\\(", "").replaceAll("\\)", ""));
                if (Objects.equals(200, ticketObject.getInteger("code"))) {
                    ticket = ticketObject.getString("ticket");
                    LOGGER.debug("load ticket: {}", ticket);
                    break;
                }

                String msg = ticketObject.getString("msg");
                if (msg.equals("二维码过期，请重新扫描")) {
                    //重新加载
                    throw new IllegalArgumentException("二维码过期重新生产");
                }

                LOGGER.info("load ticket: {}", msg);
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                LOGGER.error("exception when token -> ticket", e);
            }
        }

        //校验ticket
        headers.put("Referer", "https://passport.jd.com/uc/login?ltype=logout");
        JdHttpUtils.HttpUtilsResponse tickCheckResp = jdHttpUtils.get(String.format(UrlUtils.LOGIN_TICKET_CHECK, ticket), headers);
        LOGGER.debug(tickCheckResp.getStringBody());
    }

    /**
     * 获取基础信息.
     */
    private void getUserInfo() {
        headers.put("Referer", "https://order.jd.com/center/list.action");
        while (true) {
            String userInfoUrl = String.format(UrlUtils.BASIC_USER_INFO, UrlUtils.getJQuery(), UrlUtils.getTime());
            JdHttpUtils.HttpUtilsResponse userInfoResp = jdHttpUtils.get(userInfoUrl, headers);
            String userInfo = userInfoResp.getStringBody();
            if (userInfo.startsWith("jQuery")) {
                JSONObject userInfoObject = JSON.parseObject(userInfo.replaceAll("jQuery.+\\(", "").replaceAll("\\)", ""));
                nickName = userInfoObject.getString("nickName");
                LOGGER.info("getUserInfo: {}.", nickName);
                break;
            }
            LOGGER.warn("getUserInfo retry");
        }
    }


    /**
     * 根据 sku 获取商品信息.
     */
    private String getItemInfo(String skuId) {
        while (StringUtils.isEmpty(itemInfo.get(skuId))) {
            if (null == itemInfo.get(skuId)) {
                String itemUrl = String.format(UrlUtils.BASIC_ITEM_INFO, skuId);
                JdHttpUtils.HttpUtilsResponse itemResp = jdHttpUtils.get(itemUrl, headers);
                String stringBody = itemResp.getStringBody();
                if (stringBody.startsWith("<!DOCTYPE HTML>")) {
                    Matcher matcher = pattern.matcher(stringBody);
                    if (matcher.find()) {
                        String itemName = matcher.group();
                        itemInfo.put(skuId, itemName);
                        LOGGER.debug("getItemInfo skuId: {}, itemName: {}", skuId, itemName);
                        break;
                    }
                } else {
                    LOGGER.warn("item info load retry");
                }
            }
        }
        LOGGER.info("用户名称: {}, 商品信息: {}", nickName, itemInfo);
        return itemInfo.get(skuId);
    }


    /**
     * 提交订单.
     */
    public void seckillSubmit(String skuId) {
        MDC.put("subId", "SECKILLSUBMIT");

        //生成订单的基本信息
        headers.put("Host", "marathon.jd.com");
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("sku", skuId);
        bodyMap.put("num", jdConfig.getOrDefault("count", "1"));
        bodyMap.put("isModifyAddress", "false");
        JSONObject infoObject = null;
        while (true) {
            JdHttpUtils.HttpUtilsResponse initInfoResp = jdHttpUtils.post(UrlUtils.SECKILL_INIT_INFO, JSON.toJSONString(bodyMap), headers);
            LOGGER.debug("seckillSubmit initInfoResp: {}", initInfoResp.getStringBody());
            if (Objects.equals(200, initInfoResp.getCode()) && initInfoResp.getStringBody().startsWith("{")) {
                infoObject = JSON.parseObject(initInfoResp.getStringBody());
                LOGGER.info("initInfo success: {}", infoObject);
                break;
            }
            LOGGER.warn("initInfo fail retry");
        }

        //{"address":{"addressDetail":"科技路259号枫韵蓝湾社区西门快递驿站","areaCode":"86","cityId":2376,"cityName":"西安市","countyId":4343,"countyName":"雁塔区","defaultAddress":false,"id":4750801223,"mobile":"195****2336","mobileKey":"ac92fa89e0db6264373de377319a7e15","name":"杨赛","overseas":0,"phone":"","postCode":"","provinceId":27,"provinceName":"陕西","selected":true,"townId":53951,"townName":"丈八沟街道","yuyueAddress":false},"buyNum":2,"code":"200","invoiceInfo":{"invoiceContentType":1,"invoicePhone":"195****2336","invoicePhoneKey":"ac92fa89e0db6264373de377319a7e15","invoiceTitle":4,"invoiceType":3},"jingdouBO":{"canUseCount":1000,"jingdouDiscount":0.00,"jingdouGearList":[1000],"rate":100,"totalCount":1531,"usedCount":0},"orderPriceBO":{"couponAbleNum":0,"couponDiscount":0.00,"couponDiscountTotal":0.00,"couponSelectNum":0,"couponTypeList":[],"freight":0.00,"giftCardAbleNum":0,"giftCardDiscount":0.00,"giftCardSelectNum":0,"jingdouDiscount":0.00,"manjianDiscount":0.00,"orderTax":0.00,"productTotalPrice":1499.00,"showXuZhongInfo":false,"totalPrice":1499.00,"weight":"1.12","yunfeiDiscount":0.00},"payment":{"paymentId":4,"paymentName":"在线支付"},"seckillSkuVO":{"color":"飞天 53%vol 500ml 贵州茅台酒","extMap":{"YuShou":"1","is7ToReturn":"0","new7ToReturn":"8","thwa":"0","SoldOversea":"0"},"height":0.0,"jdPrice":1499.00,"length":0.0,"num":1,"rePrice":0.00,"size":"","skuId":100012043978,"skuImgUrl":"jfs/t1/97097/12/15694/245806/5e7373e6Ec4d1b0ac/9d8c13728cc2544d.jpg","skuName":"飞天 53%vol  500ml 贵州茅台酒（带杯）","skuPrice":1499.00,"thirdCategoryId":0.0,"venderName":"京东自营","venderType":0,"weight":1.120,"width":0.0},"shipment":{"bundleUuid":"BundleRelation_993124054960791552","obtainAllCombinationBundle":"CombinationBundleRelation_993124054767853569","obtainOrder":"1207802664","shipmentTimeType":4,"shipmentTimeTypeName":"标准达","shipmentType":65,"shipmentTypeName":"京东配送","uuid":"101612_993124054964985856"},"token":"683827e27e2d99005f00495ce06b5ce1"}
        JSONObject defaultAdressObject = infoObject.getJSONArray("addressList").getJSONObject(0);
        JSONObject invoiceInfoObject = Optional.ofNullable(infoObject.getJSONObject("invoiceInfo")).orElse(new JSONObject());
        String token = infoObject.getString("token");

        bodyMap.clear();
        bodyMap.put("skuId", skuId);
        bodyMap.put("num", jdConfig.getOrDefault("count", "1"));
        bodyMap.put("addressId", defaultAdressObject.getString("id"));
        bodyMap.put("yuShou", "true");
        bodyMap.put("isModifyAddress", "false");
        bodyMap.put("name", defaultAdressObject.getString("name"));
        bodyMap.put("provinceId", defaultAdressObject.getString("provinceId"));
        bodyMap.put("cityId", defaultAdressObject.getString("cityId"));
        bodyMap.put("countyId", defaultAdressObject.getString("countyId"));
        bodyMap.put("townId", defaultAdressObject.getString("townId"));
        bodyMap.put("addressDetail", defaultAdressObject.getString("addressDetail"));
        bodyMap.put("mobile", defaultAdressObject.getString("mobile"));
        bodyMap.put("mobileKey", defaultAdressObject.getString("mobileKey"));
        bodyMap.put("email", defaultAdressObject.getOrDefault("email", "").toString());
        bodyMap.put("postCode", "");
        bodyMap.put("invoiceTitle", invoiceInfoObject.getOrDefault("invoiceTitle", -1).toString());
        bodyMap.put("invoiceCompanyName", "");
        bodyMap.put("invoiceContent", invoiceInfoObject.getOrDefault("invoiceContentType", 1).toString());
        bodyMap.put("invoiceTaxpayerNO", "");
        bodyMap.put("invoiceEmail", "");
        bodyMap.put("invoicePhone", invoiceInfoObject.getOrDefault("invoicePhone", "").toString());
        bodyMap.put("invoicePhoneKey", invoiceInfoObject.getOrDefault("invoicePhoneKey", "").toString());
        bodyMap.put("invoice", null == infoObject.getJSONObject("invoiceInfo") ? "false" : "true");
        bodyMap.put("password", "");
        bodyMap.put("codTimeType", "3");
        bodyMap.put("paymentType", "4");
        bodyMap.put("areaCode", "");
        bodyMap.put("overseas", "0");
        bodyMap.put("phone", "");
        bodyMap.put("eid", "KWNNOWOCQDL4TMUHWLA2IFNEHCCKC7AZMXCUY2SMTGFKEFFMQEJXJYDVAHJFT7YYE75DRZSY3T6R54ENGQN7T7PJ3E");
        bodyMap.put("fp", "2224e68e0428e5fbf50f317ec8510a5f");
        bodyMap.put("token", token);
        bodyMap.put("pru", "");


        //提交订单
        headers.put("Host", "marathon.jd.com");
        headers.put("Referer", String.format("https://marathon.jd.com/seckill/seckill.action?skuId=%s&num=%s&rid=%s", skuId, jdConfig.getOrDefault("count", "1"), UrlUtils.getTime()));
        String submitUrl = String.format(UrlUtils.SECKILL_SUBMIT, skuId);
        while (true) {
            JdHttpUtils.HttpUtilsResponse submitResp = jdHttpUtils.post(submitUrl, JSON.toJSONString(bodyMap), headers);
            LOGGER.debug("seckillSubmit submitResp: {}", submitResp.getStringBody());
            if (Objects.equals(200, submitResp.getCode()) && submitResp.getStringBody().startsWith("{")) {
                JSONObject submitRespObject = JSON.parseObject(submitResp.getStringBody());
                if (submitRespObject.getBoolean("success")) {
                    //抢购成功
                    LOGGER.info("========= 抢购成功了 ======== orderId: {}, money: {}, payUrl: {}",
                            submitRespObject.getString("orderId"), submitRespObject.getString("totalMoney"), submitRespObject.getString("pcUrl"));
                    System.exit(1);
                }
                //抢购失败
                LOGGER.info("********* 抢购失败了: {}", submitRespObject);
                break;
            }
        }
    }

    /**
     * 访问抢购订单结算页面.
     */
    public void seckillCheckout(String skuId) {
        MDC.put("subId", "SECKILLCHECKOUT");
        headers.put("Host", "marathon.jd.com");
        headers.put("Referer", String.format("https://item.jd.com/%s.html", skuId));
        while (true) {
            JdHttpUtils.HttpUtilsResponse checkoutResp = jdHttpUtils.get(String.format(UrlUtils.SECKILL_CHECKOUT, skuId, jdConfig.getOrDefault("count", 1), UrlUtils.getTime()), headers);
            LOGGER.debug("seckillCheckout checkoutResp: {}", checkoutResp.getStringBody());
            if (Objects.equals(200, checkoutResp.getCode())) {
                if (checkoutResp.getStringBody().contains("填写订单")) {
                    break;
                } else {
                    LOGGER.warn("seckillCheckout request success but message is error!");
                    throw new RetryException("订单结算页面访问失败");
                }
            }
            LOGGER.warn("seckillCheckout retry");
        }
        LOGGER.info("step2: success");
    }

    /**
     * 访问按钮的抢购地址.
     */
    public void seckillBtn(String skuId) {
        MDC.put("subId", "SECKILLBTN");
        String showBtnUrl = String.format(UrlUtils.SECKILL_SHOW_BTN, UrlUtils.getJQuery(), skuId, UrlUtils.getTime());
        headers.put("Referer", String.format("https://item.jd.com/%s.html", skuId));
        headers.put("Host", "itemko.jd.com");

        //获取抢购链接
        String seckillUrl = null;
        while (true) {
            JdHttpUtils.HttpUtilsResponse showBtnResp = jdHttpUtils.get(showBtnUrl, headers);
            LOGGER.debug("showBtn showBtnRespStringBody: {}", showBtnResp.getStringBody());
            if (Objects.equals(200, showBtnResp.getCode()) && showBtnResp.getStringBody().startsWith("jQuery")) {
                JSONObject btnObject = JSON.parseObject(showBtnResp.getStringBody().replaceAll("jQuery.+\\(", "").replaceAll("\\)", ""));
                if (StringUtils.isNotEmpty(btnObject.getString("url"))) {
                    seckillUrl = "https:" + btnObject.getString("url").replace("divide", "marathon").replace("user_routing", "captcha.html");
                    LOGGER.info("seckillUrl get success. {}", seckillUrl);
                    break;
                }
            }
            LOGGER.info("showBtn request retry ");
        }

        //访问抢购链接
        while (true) {
            headers.put("Referer", String.format("https://item.jd.com/%s.html", skuId));
            headers.put("Host", "marathon.jd.com");
            //fixme 访问失效!!!
            JdHttpUtils.HttpUtilsResponse seckillUrlResp = jdHttpUtils.get(seckillUrl, headers);
            LOGGER.debug("showBtn seckillUrlResp: {}", seckillUrlResp.getStringBody());
            if (Objects.equals(200, seckillUrlResp.getCode())) {
                LOGGER.info("seckillUrl request success");
                break;
            }
            LOGGER.warn("seckillUrl request retry ");
        }
        LOGGER.info("step1: success");
    }

    public void test() {
        JdHttpUtils.HttpUtilsResponse response = jdHttpUtils.get("https://un.m.jd.com/cgi-bin/app/appjmp?tokenKey=AAEAMB0VSuXpyMCtF2UNKQ-Jyxi3SIbRpibf0S2gL9vgyNPF5zbYH94gSBf3-RSi4W6dMg1&to=https://divide.jd.com/user_routing?skuId=100012043978&from=app&lbs={\"lat\":\"34.214244\",\"lng\":\"108.88742\",\"provinceId\":\"27\",\"cityId\":\"2376\",\"districtId\":\"4343\",\"provinceName\":\"陕西\",\"cityName\":\"西安市\",\"districtName\":\"雁塔区\"}", headers);
        String stringBody = response.getStringBody();
        Headers heads = response.getHeads();
        LOGGER.info("stringBody: {}", stringBody);
        LOGGER.info("heads: {}", heads);
    }


}
