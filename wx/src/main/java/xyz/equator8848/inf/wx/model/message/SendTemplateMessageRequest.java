package xyz.equator8848.inf.wx.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
public class SendTemplateMessageRequest {
    @JsonProperty("touser")
    private String toUser;

    @JsonProperty("template_id")
    private String templateId;

    /**
     * URL置空，则在发送后，点击模板消息会进入一个空白页面（ios），或无法点击（android）
     */
    private String url;

    @JsonProperty("topcolor")
    private String topColor;

    private Map<String, DataItem> data;

    @Data
    @Builder
    public static class DataItem {
        private String value;

        private String color = "#173177";
    }
}
