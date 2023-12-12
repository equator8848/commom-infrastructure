package xyz.equator8848.inf.email.config;

import lombok.Data;
import xyz.equator8848.inf.email.service.impl.AliEmailConfiguration;

import java.util.List;

/**
 * @Author: Equator
 * @Date: 2021/11/21 23:36
 **/
@Data
public class EmailConfiguration {
    /**
     * Ali、EnterpriseWx
     */
    private String emailService;

    /**
     * 发送者邮箱
     */
    private String sender;

    /**
     * 发送者
     */
    private String senderPersonal;

    /**
     * 观察者
     */
    private String observer;

    /**
     * 邮箱授权码
     */
    private String authCode;

    /**
     * 管理员邮箱
     */
    private List<String> administrators;

    /**
     * 阿里云邮件服务配置
     */
    private AliEmailConfiguration aliEmailConfiguration;
}
