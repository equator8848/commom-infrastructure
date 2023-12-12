package xyz.equator8848.inf.auth.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登录响应
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "登录响应 VO", description = "登录响应 VO")
public class LoginResultVO {
    @ApiModelProperty("鉴权token")
    private String token;

    @ApiModelProperty("过期时间")
    private Long expireTime;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("登录IP")
    private String loginIp;
}
