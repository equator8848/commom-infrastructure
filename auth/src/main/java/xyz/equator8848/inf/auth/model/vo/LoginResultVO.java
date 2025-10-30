package xyz.equator8848.inf.auth.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登录响应
 */
@Data
@Accessors(chain = true)
public class LoginResultVO {
    private String token;

    private Long expireTime;

    private String account;

    private String name;

    private String loginIp;
}
