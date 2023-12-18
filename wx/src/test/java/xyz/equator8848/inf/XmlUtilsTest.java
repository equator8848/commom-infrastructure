package xyz.equator8848.inf;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.junit.Test;
import xyz.equator8848.inf.core.util.json.JsonUtil;
import xyz.equator8848.inf.wx.model.message.WxDefaultMessage;
import xyz.equator8848.inf.wx.util.XmlUtils;

public class XmlUtilsTest {
    @Test
    public void test() throws DocumentException {
        String xml = "<xml><ToUserName><![CDATA[gh_7bac36915a38]]></ToUserName>\n" +
                "<FromUserName><![CDATA[o74-A528nvSXkx6BpFIjno2GVvgs]]></FromUserName>\n" +
                "<CreateTime>1702737954</CreateTime>\n" +
                "<MsgType><![CDATA[event]]></MsgType>\n" +
                "<Event><![CDATA[SCAN]]></Event>\n" +
                "<EventKey><![CDATA[LOGIN_1702737947878]]></EventKey>\n" +
                "<Ticket><![CDATA[gQFb8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyWUI3c0ltTGZlMEQxckhXWk5CYzIAAgQbuH1lAwTQAgAA]]></Ticket>\n" +
                "</xml>";
        Document document = DocumentHelper.parseText(xml);
        ObjectNode receiveMessageJson = XmlUtils.elementToJSONObject(document.getRootElement());
        System.out.println(receiveMessageJson.toString());
        WxDefaultMessage receiveMessage = JsonUtil.fromJson(receiveMessageJson, WxDefaultMessage.class);
        System.out.println(receiveMessage);

        String json = "{\"ToUserName\":\"gh_7bac36915a38\",\"FromUserName\":\"o74-A528nvSXkx6BpFIjno2GVvgs\",\"CreateTime\":\"1702737954\",\"MsgType\":\"event\",\"Event\":\"SCAN\",\"EventKey\":\"LOGIN_1702737947878\",\"Ticket\":\"gQFb8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyWUI3c0ltTGZlMEQxckhXWk5CYzIAAgQbuH1lAwTQAgAA\"}";
        System.out.println(JsonUtil.fromJson(json, WxDefaultMessage.class));
    }
}
