package xyz.equator8848.inf.email.model;

import lombok.Data;

import java.util.Set;


/**
 * @author LBJ
 * 邮箱元素
 */
@Data
public class Email {
    /**
     * 接收人
     */
    private String receiver;

    /**
     * 批量接收人
     */
    private Set<String> receivers;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 阿里云 标签名称
     */
    private String tagName;
}
