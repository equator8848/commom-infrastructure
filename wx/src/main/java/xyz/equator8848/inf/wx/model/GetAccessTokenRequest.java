package xyz.equator8848.inf.wx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * force_refresh 强制刷新，默认为false
 */
@Data
public class GetAccessTokenRequest {
    @JsonProperty("grant_type")
    private String grantType = "client_credential";

    /**
     * 凭证有效时间，单位：秒。目前是7200秒之内的值。
     */
    @JsonProperty("appid")
    private String appId;

    @JsonProperty("secret")
    private String secret;
}

