package xyz.equator8848.inf.core;

import xyz.equator8848.inf.core.dynamic.properties.BaseConfig;
import xyz.equator8848.inf.core.util.jwt.JwtUtil;
import xyz.equator8848.inf.core.dynamic.properties.AuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AuthConfig.class, BaseConfig.class})
public class InfCoreAutoConfiguration {

    @Bean
    @ConditionalOnWebApplication
    public JwtUtil jwtUtil(@Autowired AuthConfig authConfig) {
        return new JwtUtil(authConfig);
    }
}
