package xyz.equator8848.inf.wx.service.impl;


import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.equator8848.inf.core.model.exception.VerifyException;
import xyz.equator8848.inf.core.util.json.JsonUtil;
import xyz.equator8848.inf.wx.model.hook.VerifyRequest;
import xyz.equator8848.inf.wx.model.hook.WxVerifyRequest;
import xyz.equator8848.inf.wx.model.message.WxDefaultMessage;
import xyz.equator8848.inf.wx.model.message.WxEventMessage;
import xyz.equator8848.inf.wx.model.message.WxMessage;
import xyz.equator8848.inf.wx.model.props.WxConfiguration;
import xyz.equator8848.inf.wx.service.WebHookHandler;
import xyz.equator8848.inf.wx.service.WxEventHandler;
import xyz.equator8848.inf.wx.util.SignUtil;
import xyz.equator8848.inf.wx.util.XmlUtils;

import java.util.List;
import java.util.Optional;

import static xyz.equator8848.inf.wx.model.message.WxTextMessage.buildTextResponse;


/**
 * 需要启动服务器配置
 */
@Slf4j
@Service
public class WxHandler implements WebHookHandler {
    @Autowired
    private WxConfiguration wxConfiguration;

    @Override
    public String verify(VerifyRequest verifyRequest) {
        log.info("verify {}", verifyRequest);
        WxVerifyRequest wxVerifyRequest = (WxVerifyRequest) verifyRequest;
        log.info("wxVerifyRequest {}", wxVerifyRequest);
        if (SignUtil.checkSignature(wxConfiguration, wxVerifyRequest.getSignature(), wxVerifyRequest.getTimestamp(), wxVerifyRequest.getNonce())) {
            return wxVerifyRequest.getEchostr();
        }
        return "";
    }

    private List<WxEventHandler> wxEventHandlers;

    @Autowired
    public void initWxEventHandlers(List<WxEventHandler> wxEventHandlers) {
        this.wxEventHandlers = wxEventHandlers;
    }


    /**
     * https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Receiving_standard_messages.html#%E6%96%87%E6%9C%AC%E6%B6%88%E6%81%AF
     *
     * @param data
     * @return
     */
    @Override
    public String onMessage(String data) throws Exception {
        Document document = DocumentHelper.parseText(data);
        ObjectNode receiveMessageJson = XmlUtils.elementToJSONObject(document.getRootElement());
        WxDefaultMessage receiveMessage = JsonUtil.fromJson(receiveMessageJson.toString(), WxDefaultMessage.class);
        log.info("on wx message {}", data);
        if (receiveMessage.getMsgType().equals(WxMessage.MessageType.TEXT)) {
            String response = buildTextResponse(wxConfiguration.getOriginalId(), receiveMessage.getFromUserName(), Optional.of(wxConfiguration.getDefaultWelcomeTips()).orElse("你好"));
            log.info("wx onMessage response {}", response);
            return response;
        } else if (receiveMessage.getMsgType().equals(WxMessage.MessageType.EVENT)) {
            WxEventMessage wxEventMessage = JsonUtil.fromJson(receiveMessageJson.toString(), WxEventMessage.class);
            try {
                return handleEventMessage(wxEventMessage);
            } catch (VerifyException e) {
                return buildTextResponse(wxConfiguration.getOriginalId(), receiveMessage.getFromUserName(), e.getMessage());
            }
        } else {
            return buildTextResponse(wxConfiguration.getOriginalId(), receiveMessage.getFromUserName(), "暂时不能处理你发的消息");
        }
    }

    public String handleEventMessage(WxEventMessage wxEventMessage) {
        for (WxEventHandler wxEventHandler : wxEventHandlers) {
            if (wxEventHandler.canHandle(wxEventMessage)) {
                return wxEventHandler.handle(wxEventMessage);
            }
        }
        return "";
    }
}