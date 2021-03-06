package com.fzu.gcxl.daocloud.infrastructure.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fzu.gcxl.daocloud.domain.exception.UserFriendException;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Component
public class JwtUtil {

    //token过期时间
    private static String accessTokenExpireTime = "3600";
    //JWT密钥
    private static String encryptJWTKey = "U0JBUElKV1RkV2FuZzkyNjQ1NA==";

    public static boolean verify(String token) {
        try {
            System.out.println("验证token"+token);
            // 帐号加JWT私钥解密
            String secret = getClaim(token, "account") + Base64ConvertUtil.decode(encryptJWTKey);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            //logger.error("JWTToken认证解密出现UnsupportedEncodingException异常:{}", e.getMessage());
            throw new UserFriendException("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }

    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            //logger.error("解密Token中的公共信息出现JWTDecodeException异常:{}", e.getMessage());
            throw new UserFriendException("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }

    public static String sign(String account, String currentTimeMillis) {
        try {
            // 帐号加JWT私钥加密
            String secret = account + Base64ConvertUtil.decode(encryptJWTKey);
            // 此处过期时间是以毫秒为单位，所以乘以1000
            Date date = new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 1000 * 24 * 7);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带account帐号信息
            return JWT.create()
                    .withClaim("account", account)
                    .withClaim("currentTimeMillis", currentTimeMillis)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            //logger.error("JWTToken加密出现UnsupportedEncodingException异常:{}", e.getMessage());
            throw new UserFriendException("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }
}
