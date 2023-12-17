package xyz.equator8848.inf.wx.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.dom4j.Element;
import org.dom4j.tree.DefaultCDATA;
import xyz.equator8848.inf.wx.model.constant.MessageType;

import java.time.Instant;

@Data
@ToString(callSuper = true)
public class WxTextMessage extends WxMessage {
    @JsonProperty("Content")
    private String Content;

    @Override
    public void addElement(Element root) {
        root.addElement("Content").add(new DefaultCDATA(Content));
    }

    public static String buildTextResponse(String fromUserName, String toUserName, String content) {
        WxTextMessage response = new WxTextMessage();
        response.setContent(content);
        response.setFromUserName(fromUserName);
        response.setToUserName(toUserName);
        response.setCreateTime(String.valueOf(Instant.now().getEpochSecond()));
        response.setMsgType(MessageType.TEXT);
        return response.buildMessageXml();
    }
}
