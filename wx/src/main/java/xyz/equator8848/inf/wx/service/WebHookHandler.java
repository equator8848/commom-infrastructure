package xyz.equator8848.inf.wx.service;


import xyz.equator8848.inf.wx.model.hook.VerifyRequest;

public interface WebHookHandler {
    Object verify(VerifyRequest verifyRequest);

    Object onMessage(String data) throws Exception;
}
