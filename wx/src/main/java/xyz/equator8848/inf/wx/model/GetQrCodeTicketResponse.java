package xyz.equator8848.inf.wx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetQrCodeTicketResponse {
    @JsonProperty("expire_seconds")
    private Integer expireSeconds;

    private String ticket;

    private String url;
}
