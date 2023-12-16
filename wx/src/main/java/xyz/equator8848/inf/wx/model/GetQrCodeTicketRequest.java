package xyz.equator8848.inf.wx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * {"expire_seconds":604800,"action_name":"QR_SCENE","action_info":{"scene":{"scene_id":123}}}
 */
@Data
public class GetQrCodeTicketRequest {
    @JsonProperty("expire_seconds")
    private Integer expireSeconds;
    /**
     * 二维码类型，QR_SCENE为临时的整型参数值，QR_STR_SCENE为临时的字符串参数值，QR_LIMIT_SCENE为永久的整型参数值，QR_LIMIT_STR_SCENE为永久的字符串参数值
     */
    @JsonProperty("action_name")
    private String actionName;

    @JsonProperty("action_info")
    private ActionInfo actionInfo;

    public static class ActionName {
        public static final String QR_STR_SCENE = "QR_STR_SCENE";
    }

    @Data
    @Builder
    public static class ActionInfo {
        private Scene scene;
    }

    @Data
    @Builder
    public static class Scene {
        @JsonProperty("scene_id")
        private Integer sceneId;

        @JsonProperty("scene_str")
        private String sceneStr;
    }
}
