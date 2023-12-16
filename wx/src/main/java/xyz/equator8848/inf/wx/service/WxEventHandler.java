package xyz.equator8848.inf.wx.service;


import xyz.equator8848.inf.wx.model.message.WxEventMessage;

public interface WxEventHandler {
    boolean canHandle(WxEventMessage wxEventMessage);

    String handle(WxEventMessage wxEventMessage);
}
