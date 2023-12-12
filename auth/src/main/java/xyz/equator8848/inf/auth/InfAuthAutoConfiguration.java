package xyz.equator8848.inf.auth;

import xyz.equator8848.inf.auth.util.UserAuthUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfAuthAutoConfiguration {
    @Bean
    public UserAuthUtil userAuthUtil() {
        return new UserAuthUtil();
    }
}
