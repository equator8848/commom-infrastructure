package xyz.equator8848.inf.wx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * {"access_token":"ACCESS_TOKEN","expires_in":7200}
 */
@Data
public class GetAccessTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Integer expiresIn;
}
