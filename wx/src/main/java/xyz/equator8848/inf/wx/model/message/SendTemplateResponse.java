package xyz.equator8848.inf.wx.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SendTemplateResponse {
    private Integer errcode;

    private String errmsg;

    @JsonProperty("msgid")
    private Long msgId;
}
