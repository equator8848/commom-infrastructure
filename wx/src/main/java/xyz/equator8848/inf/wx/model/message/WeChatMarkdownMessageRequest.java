package xyz.equator8848.inf.wx.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeChatMarkdownMessageRequest {
    @JsonProperty("msgtype")
    private String msgType;

    private Markdown markdown;

    @Data
    public static class Markdown {
        private String content;
    }
}
