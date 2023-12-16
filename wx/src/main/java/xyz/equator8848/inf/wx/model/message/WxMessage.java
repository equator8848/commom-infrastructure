package xyz.equator8848.inf.wx.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultCDATA;
import org.dom4j.tree.DefaultText;

@Data
public abstract class WxMessage {
    @JsonProperty("ToUserName")
    private String ToUserName;

    @JsonProperty("FromUserName")
    private String FromUserName;

    @JsonProperty("CreateTime")
    private String CreateTime;

    @JsonProperty("MsgType")
    private String MsgType;

    @JsonProperty("MsgId")
    private String MsgId;

    public String buildMessageXml() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("xml");
        if (StringUtils.isNotEmpty(ToUserName)) {
            root.addElement("ToUserName").add(new DefaultCDATA(ToUserName));
        }
        if (StringUtils.isNotEmpty(FromUserName)) {
            root.addElement("FromUserName").add(new DefaultCDATA(FromUserName));
        }
        if (StringUtils.isNotEmpty(CreateTime)) {
            root.addElement("CreateTime").add(new DefaultText(CreateTime));
        }
        if (StringUtils.isNotEmpty(MsgType)) {
            root.addElement("MsgType").add(new DefaultCDATA(MsgType));
        }
        addElement(root);
        return document.getRootElement().asXML();
    }

    public abstract void addElement(Element root);

    public static final class MessageType {
        public static final String TEXT = "text";

        public static final String EVENT = "event";
    }

    public static final class EventType {
        public static final String SCAN = "SCAN";

        public static final String SUBSCRIBE = "subscribe";

        public static final String UNSUBSCRIBE = "unsubscribe";
    }
}
