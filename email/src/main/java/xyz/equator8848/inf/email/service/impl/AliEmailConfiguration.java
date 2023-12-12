package xyz.equator8848.inf.email.service.impl;

import lombok.Data;

@Data
public class AliEmailConfiguration {
    private String accessKey;

    private String secretKey;

    private String endpoint = "dm.aliyuncs.com";

    private String accountName;

    private String fromAlias;
}
