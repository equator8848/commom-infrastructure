package xyz.equator8848.inf.core.dynamic.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "equator.inf.config.auth")
public class AuthConfig {
    /**
     * JWT过期时间，默认30天
     */
    @Value("${equator.inf.config.auth.jwt-token-expire-ms:2592000000}")
    private Long jwtTokenExpireMs;

    /**
     * JWT加密密钥
     */
    @Value("${equator.inf.config.auth.jwt-token-secret:530dee45151fa0af45bab666ea9f9e70}")
    private String jwtTokenSecret;
}
