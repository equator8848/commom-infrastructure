package xyz.equator8848.inf.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import xyz.equator8848.inf.core.dynamic.properties.BaseConfig;
import xyz.equator8848.inf.email.config.EmailConfiguration;
import xyz.equator8848.inf.email.service.impl.AliEmailService;
import xyz.equator8848.inf.email.service.impl.EnterpriseWxEmailService;

@ComponentScan
@Configuration
@EnableConfigurationProperties({EmailConfiguration.class})
public class InfEmailAutoConfiguration {
    @Bean
    public AliEmailService aliEmailService(@Autowired EmailConfiguration emailConfiguration,
                                           @Autowired BaseConfig baseConfig) {
        return new AliEmailService(emailConfiguration, baseConfig.getEnv());
    }

    @Bean
    public EnterpriseWxEmailService enterpriseWxEmailService(@Autowired EmailConfiguration emailConfiguration,
                                                             @Autowired BaseConfig baseConfig) {
        return new EnterpriseWxEmailService(emailConfiguration, baseConfig.getEnv());
    }
}
