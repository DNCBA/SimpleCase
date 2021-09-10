package com.mhc.jd.utils;

import org.apache.commons.lang3.RandomStringUtils;


public class UrlUtils {

    //登录相关
    public static final String LOGIN_ASPX = "https://passport.jd.com/new/login.aspx";
    public static final String LOGIN_QR_SHOW = "https://qr.m.jd.com/show?appid=133&size=147&t=%s";
    public static final String LOGIN_QR_CHECK = "https://qr.m.jd.com/check?callback=%s&appid=133&token=%s&_=%s";
    public static final String LOGIN_TICKET_CHECK = "https://passport.jd.com/uc/qrCodeTicketValidation?t=%s";
    public static final String LOGIN_STATUS_CHECK = "https://order.jd.com/center/list.action?rid=%s";

    //预约相关
    public static final String RESERVE_FETCH = "https://yushou.jd.com/youshouinfo.action?callback=fetchJSON&sku=%s";

    //抢购相关
    public static final String SECKILL_SHOW_BTN = "https://itemko.jd.com/itemShowBtn?callback=%s&skuId=%s&from=pc&_=%s";
    /**
     * old: https://marathon.jd.com/seckill/seckill.action?skuId=%s&num=%s&rid=%s.
     */
    public static final String SECKILL_CHECKOUT = "https://marathon.jd.com/seckillM/seckill.action?skuId=%s&num=%s&rid=%s";
    public static final String SECKILL_SUBMIT = "https://marathon.jd.com/seckillnew/orderService/pc/submitOrder.action?skuId=%s";


    /**
     * old: https://marathon.jd.com/seckillnew/orderService/pc/init.action.
     */
    public static final String SECKILL_INIT_INFO = "https://marathon.jd.com/seckillnew/orderService/init.action";

    //基础信息
    public static final String BASIC_USER_INFO = "https://passport.jd.com/user/petName/getUserInfoForMiniJd.action?callback=%s&_=%s";
    public static final String BASIC_ITEM_INFO = "https://item.jd.com/%s.html";


    public static String getJQuery() {
        return getJQuery(7);
    }

    public static String getJQuery(Integer size) {
        return "jQuery" + RandomStringUtils.randomNumeric(size);
    }

    public static String getTime() {
        return "" + System.currentTimeMillis();
    }


}
