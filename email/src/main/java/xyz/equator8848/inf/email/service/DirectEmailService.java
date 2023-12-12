package xyz.equator8848.inf.email.service;

import xyz.equator8848.inf.email.model.Email;

public interface DirectEmailService {
    /**
     * 发送单个邮件
     * @param email
     */
    void sendEmail(Email email);

    /**
     * 发送全员邮件
     *
     * @param email
     */
    void sendEmailToAll(Email email);
}
