package com.mhc.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ISSUER = "签发人";
    public static final String SECRET = "密匙";
    public static final String JWT_TOKEN_FIELD_STORE_USER_NAME = "username";
    public static final Long TOKEN_EXPIRES = 60 * 60 * 1000L;
    public static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    public static final JWTVerifier JWTVERIFIER = JWT.require(ALGORITHM).withIssuer(ISSUER).build();

    /**
     * parse jwt token.
     */
    public static DecodedJWT parseJWTToken(String token) {
        String data = token.substring(TOKEN_PREFIX.length());
        //验证是否失效
        JWTVERIFIER.verify(data);
        //去掉token开头的Bearer和空格
        return JWT.decode(data);
    }

    /**
     * tokenConvert.
     */
    public static String genToken(String username) {
        Date date = new Date(System.currentTimeMillis() + TOKEN_EXPIRES);
        JWTCreator.Builder builder = JWT.create();
        builder.withIssuer(ISSUER);
        builder.withExpiresAt(date);
        builder.withClaim(JWT_TOKEN_FIELD_STORE_USER_NAME, username);
        return builder.sign(ALGORITHM);
    }

}

