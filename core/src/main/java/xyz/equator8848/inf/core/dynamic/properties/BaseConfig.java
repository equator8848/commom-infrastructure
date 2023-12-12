package xyz.equator8848.inf.core.dynamic.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "equator.inf.config.base")
public class BaseConfig {
    /**
     * 系统名称
     */
    @Value("${equator.inf.config.base.systemName:equatorSystem}")
    private String systemName;
}
