package xyz.equator8848.inf.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import xyz.equator8848.inf.core.dynamic.properties.AuthConfig;
import xyz.equator8848.inf.core.dynamic.properties.BaseConfig;
import xyz.equator8848.inf.core.util.jwt.JwtUtil;

@ComponentScan
@Configuration
@EnableConfigurationProperties({AuthConfig.class, BaseConfig.class})
public class InfCoreAutoConfiguration {

    @Bean
    public JwtUtil jwtUtil(@Autowired AuthConfig authConfig) {
        return new JwtUtil(authConfig);
    }
}
