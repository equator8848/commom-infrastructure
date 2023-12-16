package xyz.equator8848.inf.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.equator8848.inf.auth.util.UserAuthUtil;

@Configuration
public class InfAuthAutoConfiguration {
    @Bean
    public UserAuthUtil userAuthUtil() {
        return new UserAuthUtil();
    }
}
