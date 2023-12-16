package xyz.equator8848.inf.wx.service;

import xyz.equator8848.inf.wx.model.GetAccessTokenResponse;
import xyz.equator8848.inf.wx.model.exception.NeedSubscribeException;
import xyz.equator8848.inf.wx.model.message.SendTemplateMessageRequest;

public interface WxExternalService {


    /**
     * 获取token，不会相互覆盖
     *
     * @return
     */
    GetAccessTokenResponse getStableAccessTokenFromWx();

    String getAccessToken();

    String getQrCode(String sceneStr);

    String getLoginQrCode(String loginRandomId);

    Long sendTemplateMessage(SendTemplateMessageRequest sendTemplateMessageRequest) throws NeedSubscribeException;
}
