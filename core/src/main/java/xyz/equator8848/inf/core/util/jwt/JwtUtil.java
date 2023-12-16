package xyz.equator8848.inf.core.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.tuple.Pair;
import xyz.equator8848.inf.core.dynamic.properties.AuthConfig;

import java.util.Date;
import java.util.Map;

/**
 * @Author: Equator
 * @Date: 2021/1/29 19:19
 **/
public class JwtUtil {
    private final AuthConfig authConfig;

    /**
     * 加密算法
     */
    private final Algorithm algorithm;

    /**
     * token 验证器
     */
    private final JWTVerifier verifier;

    public JwtUtil(AuthConfig authConfig) {
        this.authConfig = authConfig;
        algorithm = Algorithm.HMAC256(authConfig.getJwtTokenSecret());
        verifier = JWT.require(algorithm).build();
    }

    public String code(Map<String, String> payload) {
        String token;
        try {
            // 过期时间
            Date expiresAt = new Date(System.currentTimeMillis() + authConfig.getJwtTokenExpireMs());
            /*
             * 默认的头部信息
             * {
             *     "typ": "JWT",
             *     "alg": "HS256
             * }
             *
             */
            JWTCreator.Builder builder = JWT.create();
            payload.forEach(builder::withClaim);
            token = builder.withExpiresAt(expiresAt).sign(algorithm);
        } catch (Exception e) {
            return null;
        }
        return token;
    }

    public Pair<String, Date> codeWithExpiredTime(Map<String, String> payload) {
        String token;
        try {
            // 过期时间
            Date expiresAt = new Date(System.currentTimeMillis() + authConfig.getJwtTokenExpireMs());
            /*
             * 默认的头部信息
             * {
             *     "typ": "JWT",
             *     "alg": "HS256
             * }
             *
             */
            JWTCreator.Builder builder = JWT.create();
            payload.forEach(builder::withClaim);
            token = builder.withExpiresAt(expiresAt).sign(algorithm);
            return Pair.of(token, expiresAt);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证token，通过返回解码后的JWT
     *
     * @param token 需要校验的串
     * @return
     */
    public DecodedJWT decode(String token) {
        return verifier.verify(token);
    }

    /**
     * JWT解析
     *
     * @param jwtToken
     * @return
     */
    public static DecodedJWT parse(String jwtToken) {
        return JWT.decode(jwtToken);
    }
}
