package xyz.equator8848.inf.email.service.impl;


import xyz.equator8848.inf.core.model.exception.InnerException;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;
import xyz.equator8848.inf.email.config.EmailConfiguration;
import xyz.equator8848.inf.email.model.Email;
import xyz.equator8848.inf.email.service.DirectEmailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

/**
 * 企业微信邮箱
 *
 * @author LBJ
 */
@Slf4j
public class EnterpriseWxEmailService implements DirectEmailService {
    private final Properties mailSetting;

    private final EmailConfiguration emailConfiguration;

    private final String env;

    public EnterpriseWxEmailService(EmailConfiguration emailConfiguration, String env) {
        mailSetting = new Properties();
        // 邮箱协议
        mailSetting.setProperty("mail.transport.protocol", "smtp");
        // 邮箱主机
        mailSetting.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
        // 端口号
        mailSetting.setProperty("mail.smtp.port", "465");
        // 开启验证
        mailSetting.setProperty("mail.smtp.auth", "true");
        // SSL加密
        mailSetting.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailSetting.setProperty("mail.smtp.socketFactory.fallback", "true");
        mailSetting.setProperty("mail.smtp.socketFactory.port", "465");
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e) {
            log.info("init email error");
        }
        mailSetting.put("mail.smtp.ssl.enable", "true");
        mailSetting.put("mail.smtp.ssl.socketFactory", sf);
        this.emailConfiguration = emailConfiguration;
        this.env = env;
    }

    @Override
    public void sendEmail(Email email) {
        try {
            // 开启会话
            Session session = Session.getDefaultInstance(mailSetting, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailConfiguration.getSender(), emailConfiguration.getAuthCode());
                }
            });
            // 开启debug模式
            session.setDebug(false);
            MimeMessage message = createMimeMessage(session, emailConfiguration.getSender(), emailConfiguration.getSenderPersonal(), email);
            Transport transport = session.getTransport();
            transport.connect(emailConfiguration.getSender(), emailConfiguration.getAuthCode());
            Transport.send(message);
        } catch (Exception e) {
            log.error("EnterpriseWxEmailService sendEmail failed {}", email, e);
            throw new InnerException("EnterpriseWxEmailService sendEmail failed");
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

    private MimeMessage createMimeMessage(Session session, String sender, String senderPersonal, Email email) throws UnsupportedEncodingException, javax.mail.MessagingException {
        MimeMessage message = new MimeMessage(session);
        Address fromAddress = new InternetAddress(sender, senderPersonal, StandardCharsets.UTF_8.displayName());
        message.setFrom(fromAddress);
        message.setSubject(String.format("【环境%s】%s", env, email.getTitle()), StandardCharsets.UTF_8.displayName());
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email.getReceiver(), "receiver", StandardCharsets.UTF_8.displayName()));
        message.setSentDate(new Date());
        message.setContent(email.getContent(), "text/html;charset=utf-8");
        message.saveChanges();
        return message;
    }
}
