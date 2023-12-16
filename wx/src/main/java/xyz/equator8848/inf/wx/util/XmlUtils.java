package xyz.equator8848.inf.wx.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.dom4j.*;
import xyz.equator8848.inf.core.util.json.JsonUtil;

import java.io.IOException;
import java.util.List;

public class XmlUtils {
    private static Document strToDocument(String xml) throws DocumentException {
        return DocumentHelper.parseText(xml);
    }

    /**
     * xml 转  com.alibaba.fastjson.JSONObject
     *
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static ObjectNode documentToJSONObject(String xml) {
        ObjectNode jsonObject = null;
        try {
            return elementToJSONObject(strToDocument(xml).getRootElement());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * org.dom4j.Element 转  com.alibaba.fastjson.JSONObject
     *
     * @param node
     * @return
     */
    public static ObjectNode elementToJSONObject(Element node) {
        ObjectNode result = JsonUtil.getJsonObject();
        // 当前节点的名称、文本内容和属性
        // // 当前节点的所有属性的list
        List<Attribute> attributes = node.attributes();
        // 遍历当前节点的所有属性
        for (Attribute attr : attributes) {
            result.put(attr.getName(), attr.getValue());
        }
        // 递归遍历当前节点所有的子节点， 所有一级子节点的list
        List<Element> children = node.elements();
        if (!children.isEmpty()) {
            // 遍历所有一级子节点
            for (Element e : children) {
                // 判断一级节点是否有属性和子节点
                if (e.attributes().isEmpty() && e.elements().isEmpty()) {
                    // 沒有则将当前节点作为上级节点的属性对待
                    result.put(e.getName(), e.getTextTrim());
                } else {
                    // 判断父节点是否存在该一级节点名称的属性
                    if (!result.has(e.getName())) {
                        // 没有则创建
                        result.putIfAbsent(e.getName(), elementToJSONObject(e));
                    }
                }
            }
        }
        return result;
    }

    public static String jsonToXml(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(jsonString);
        } catch (IOException e) {
            return "";
        }
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(jsonNode);
    }
}
