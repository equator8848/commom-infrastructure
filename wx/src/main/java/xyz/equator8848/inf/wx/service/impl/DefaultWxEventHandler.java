package xyz.equator8848.inf.wx.service.impl;

import org.springframework.stereotype.Service;
import xyz.equator8848.inf.wx.model.message.WxEventMessage;
import xyz.equator8848.inf.wx.service.WxEventHandler;

@Service
public class DefaultWxEventHandler implements WxEventHandler {
    @Override
    public boolean canHandle(WxEventMessage wxEventMessage) {
        return false;
    }

    @Override
    public String handle(WxEventMessage wxEventMessage) {
        return null;
    }
}
