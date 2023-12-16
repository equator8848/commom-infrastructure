package xyz.equator8848.inf.wx.model.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Equator
 * @Date: 2021/11/21 23:36
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "equator.inf.wx.open")
public class WxConfiguration {

    private String wxApiHost;

    private String appId;

    private String originalId;

    private String appSecret;

    private String token;

    private String encodingAesKey;

    /**
     * 管理员报警群
     */
    private String alertWebHookUrl;

    /**
     * 欢迎语
     */
    private String defaultWelcomeTips;
}
