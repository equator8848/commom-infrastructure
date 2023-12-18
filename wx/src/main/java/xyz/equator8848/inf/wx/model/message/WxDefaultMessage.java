package xyz.equator8848.inf.wx.model.message;

import lombok.Data;
import lombok.ToString;
import org.dom4j.Element;

@Data
@ToString(callSuper = true)
public class WxDefaultMessage extends WxMessage {
    @Override
    public void addElement(Element root) {

    }
}
