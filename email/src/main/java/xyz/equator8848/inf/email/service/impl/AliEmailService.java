package xyz.equator8848.inf.email.service.impl;


import com.aliyun.dm20151123.models.SingleSendMailResponse;
import xyz.equator8848.inf.core.model.exception.InnerException;
import xyz.equator8848.inf.core.model.exception.PreCondition;
import lombok.extern.slf4j.Slf4j;
import xyz.equator8848.inf.email.config.EmailConfiguration;
import xyz.equator8848.inf.email.model.Email;
import xyz.equator8848.inf.email.service.DirectEmailService;
import xyz.equator8848.inf.email.service.impl.AliEmailConfiguration;

/**
 * 阿里云邮件服务
 *
 * @author LBJ
 */
@Slf4j
public class AliEmailService implements DirectEmailService {

    private final EmailConfiguration emailConfiguration;

    private final String env;

    public AliEmailService(EmailConfiguration emailConfiguration, String env) {
        this.emailConfiguration = emailConfiguration;
        this.env = env;
    }

    @Override
    public void sendEmail(Email email) {
        AliEmailConfiguration aliEmailConfiguration = emailConfiguration.getAliEmailConfiguration();
        PreCondition.isNotNull(aliEmailConfiguration, "阿里云邮件配置不能为空");
        com.aliyun.dm20151123.Client client = createClient();
        com.aliyun.dm20151123.models.SingleSendMailRequest singleSendMailRequest = new com.aliyun.dm20151123.models.SingleSendMailRequest()
                .setAddressType(1)
                .setAccountName(aliEmailConfiguration.getAccountName())
                .setTagName(email.getTagName())
                .setReplyToAddress(false)
                .setSubject(String.format("【环境%s】%s", env, email.getTitle()))
                .setHtmlBody(email.getContent())
                .setFromAlias(aliEmailConfiguration.getFromAlias())
                .setToAddress(email.getReceiver());
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            SingleSendMailResponse singleSendMailResponse = client.singleSendMailWithOptions(singleSendMailRequest, runtime);
            log.info("AliEmailService sendEmail response {}, status code {}", email, singleSendMailResponse.getStatusCode());
        } catch (Exception e) {
            log.error("AliEmailService sendEmail failed {}", email, e);
            throw new InnerException("AliEmailService sendEmail failed");
        }
    }

    /**
     * 发送全员邮件
     *
     * @param email
     */
    @Override
    public void sendEmailToAll(Email email) {
        for (String receiver : email.getReceivers()) {
            email.setReceiver(receiver);
            sendEmail(email);
        }
    }

    /**
     * 使用AK&SK初始化账号Client
     *
     * @return Client
     * @throws Exception
     */
    private com.aliyun.dm20151123.Client createClient() {
        AliEmailConfiguration aliEmailConfiguration = emailConfiguration.getAliEmailConfiguration();
        PreCondition.isNotNull(aliEmailConfiguration, "阿里云邮件配置不能为空");
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                .setAccessKeyId(aliEmailConfiguration.getAccessKey())
                .setAccessKeySecret(aliEmailConfiguration.getSecretKey());
        // Endpoint 请参考 https://api.aliyun.com/product/Dm
        config.endpoint = aliEmailConfiguration.getEndpoint();
        try {
            return new com.aliyun.dm20151123.Client(config);
        } catch (Exception e) {
            log.error("AliEmailService createClient failed", e);
            throw new InnerException("客户端初始化失败");
        }
    }
}
