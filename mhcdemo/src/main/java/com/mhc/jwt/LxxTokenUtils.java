package com.mhc.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-07-03 20:35
 */
public class LxxTokenUtils {

  private static String JWT_SECRET = "SuperStartTheEndGame#@";
  private static String SUBJECT = "DIROS";
  private static String ISSUER = "flashcard-lxx";
  private static String NICK = "leyantech:北京超推";


  private static String generateToken(Map claims) {
    Date createTime = new Date();
    Date expireTime = new Date(createTime.getTime() + 7 * 24 * 60 * 60 * 1000);
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(SUBJECT)
        .setIssuedAt(createTime)
        .setExpiration(expireTime)
        .setIssuer(ISSUER)
        .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
        .compact();
  }


  public static String generateToken(String storeId, String storeName) {
    Map<String, Object> claims = new HashMap(8);
    claims.put("nick", NICK);
    claims.put("store_id", storeId);
    claims.put("store_name", storeName);
    return generateToken(claims);
  }


  public static void main(String[] args) {
    String token = generateToken("237183007", "大叔的小铺92");
    String decode = decode(token);
    System.out.println(decode);

  }

  private static String decode(String str) {
    byte[] bytes = Base64.getUrlDecoder().decode(str.split("\\.")[1]);
    String result = null;
    try {
      result = new String(bytes, "utf-8");
    } catch (Exception e) {
    }
    return result;
  }

}
