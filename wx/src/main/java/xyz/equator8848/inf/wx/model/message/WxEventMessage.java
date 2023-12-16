package xyz.equator8848.inf.wx.model.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.dom4j.Element;
import org.dom4j.tree.DefaultCDATA;

@Data
@ToString(callSuper = true)
public class WxEventMessage extends WxMessage {
    @JsonProperty("Event")
    private String Event;

    @JsonProperty("EventKey")
    private String EventKey;

    @JsonProperty("Ticket")
    private String Ticket;

    @Override
    public void addElement(Element root) {
        root.addElement("Event").add(new DefaultCDATA(Event));
        root.addElement("EventKey").add(new DefaultCDATA(EventKey));
        root.addElement("Ticket").add(new DefaultCDATA(Ticket));
    }
}
