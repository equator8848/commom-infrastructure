package xyz.equator8848.inf.wx.service;

import xyz.equator8848.inf.wx.model.message.WxTextMessage;

public interface WxTextHandler {
    boolean canHandle(WxTextMessage wxTextMessage);

    String handle(WxTextMessage wxTextMessage);
}
