package com.zzjz.zzjg.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import java.util.Date;

/**
 * @Description JWT工具类
 * @Author 房桂堂
 * @Date 2019/8/20 17:12
 */
public class JwtUtil {

    /**
     * Token过期时间 5天.
     */
    private static final long EXPIRE_TIME = 5*24*60*60*1000;

    /**
     * 校验token是否正确.
     * @param token    密钥
     * @param username 用户名
     * @param secret   用户的密码
     * @return 正确: true；不正确：false
     */
    public static boolean verify(String token, String username, String secret) {
        // 根据密码生成JWT校验器
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            // 校验TOKEN
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取用户名.
     * @param token token中包含了用户名
     * @return 用户名
     */
    public static String getUsername(String token) {
        if (StringUtils.isBlank(token)) {
            throw new IncorrectCredentialsException("缺少token");
        }
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("username").asString();
    }

    /**
     * 生成签名.
     * @param username 用户名
     * @param secret   密码
     * @return 加密的TOKEN
     */
    public static String sign(String username, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC512(secret);
        // 附带用户信息 过期时间 签发时间
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    /**
     * 获取签发时间.
     * @param token token
     * @return 签发时间
     */
    public static Date getIssuedAt(String token) {
        if (StringUtils.isBlank(token)) {
            throw new IncorrectCredentialsException("缺少token");
        }
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getIssuedAt();
    }
}
