package com.hujunchina.common;

import com.hujunchina.manager.domain.ApiKeyDO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/6/30 11:33 下午
 * @Version 1.0
 * 这是什么？
 *
 */
public class JWTUtils {

    private static ApiKeyDO apiKey = new ApiKeyDO();

    public static String createJWT(String id, String issuer, String subject, long ttlMillis){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey.getSecret());

        Key signKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now)
                .setSubject(subject).setIssuer(issuer)
                .signWith(signatureAlgorithm, signKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    public static void parseJWT(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(apiKey.getSecret()))
                .parseClaimsJws(jwt).getBody();
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
    }

    public static String createToken(String userName, String password) {
        //【1】配置参数，包括加密类型、时间
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date now = new Date(System.currentTimeMillis());
        //【2】构建信息
        byte[] apiKeyBytes = DatatypeConverter.parseBase64Binary(userName+password);

        //【3】加密
        Key signKey = new SecretKeySpec(apiKeyBytes, signatureAlgorithm.getJcaName());

        //【4】构建token
        JwtBuilder builder = Jwts.builder()
                .setId(userName)
                .setIssuedAt(now)
                .setIssuer(userName)
                .setSubject(password)
                .signWith(signatureAlgorithm, signKey);

        return builder.compact();
    }
}
