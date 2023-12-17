package xyz.equator8848.inf.wx.service.impl;

import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.equator8848.inf.cache.common.LogSimpleCacheLoader;
import xyz.equator8848.inf.cache.common.SimpleCacheElement;
import xyz.equator8848.inf.cache.guava.SimpleCacheBuilder;
import xyz.equator8848.inf.core.http.HttpUtil;
import xyz.equator8848.inf.core.model.exception.PreCondition;
import xyz.equator8848.inf.core.thread.ThreadPoolService;
import xyz.equator8848.inf.core.util.json.JsonUtil;
import xyz.equator8848.inf.wx.model.GetAccessTokenRequest;
import xyz.equator8848.inf.wx.model.GetAccessTokenResponse;
import xyz.equator8848.inf.wx.model.GetQrCodeTicketRequest;
import xyz.equator8848.inf.wx.model.GetQrCodeTicketResponse;
import xyz.equator8848.inf.wx.model.constant.SceneEnum;
import xyz.equator8848.inf.wx.model.constant.SendTemplateErrorCode;
import xyz.equator8848.inf.wx.model.exception.NeedSubscribeException;
import xyz.equator8848.inf.wx.model.message.SendTemplateMessageRequest;
import xyz.equator8848.inf.wx.model.message.SendTemplateResponse;
import xyz.equator8848.inf.wx.model.props.WxConfiguration;
import xyz.equator8848.inf.wx.service.WxExternalService;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WxExternalServiceImpl implements WxExternalService {
    @Autowired
    private WxConfiguration wxConfiguration;
    private final LoadingCache<Integer, SimpleCacheElement<String>> accessTokenCache =
            SimpleCacheBuilder.newBuilder().refreshAfterWrite(1, TimeUnit.HOURS).expireAfterWrite(1,
                    TimeUnit.HOURS).maximumSize(1).build(new LogSimpleCacheLoader<Integer, String>() {
                @Override
                public String loadData(Integer key) throws Exception {
                    return getStableAccessTokenFromWx().getAccessToken();
                }

                @Override
                public String getCacheName() {
                    return "accessTokenCache";
                }
            }, ThreadPoolService.getInstance());


    /**
     * 获取token，不会相互覆盖
     *
     * @return
     */
    @Override
    public GetAccessTokenResponse getStableAccessTokenFromWx() {
        GetAccessTokenRequest getAccessTokenRequest = new GetAccessTokenRequest();
        getAccessTokenRequest.setAppId(wxConfiguration.getAppId());
        getAccessTokenRequest.setSecret(wxConfiguration.getAppSecret());
        String response = HttpUtil.post(String.format("%s/cgi-bin/stable_token", wxConfiguration.getWxApiHost()),
                Collections.emptyMap(), JsonUtil.toJson(getAccessTokenRequest));
        log.info("getStableAccessTokenFromWx wxConfiguration {}, response {}", wxConfiguration, response);
        return JsonUtil.fromJson(response, GetAccessTokenResponse.class);
    }

    @Override
    public String getAccessToken() {
        String token = accessTokenCache.getUnchecked(0).getData();
        PreCondition.isNotNull(token, "无法获取微信请求token，请联系管理员");
        return token;
    }

    @Override
    public String getQrCode(String sceneStr) {
        try {
            GetQrCodeTicketRequest getQrCodeTicketRequest = new GetQrCodeTicketRequest();
            getQrCodeTicketRequest.setExpireSeconds(720);
            getQrCodeTicketRequest.setActionName(GetQrCodeTicketRequest.ActionName.QR_STR_SCENE);
            getQrCodeTicketRequest.setActionInfo(GetQrCodeTicketRequest
                    .ActionInfo.builder().scene(GetQrCodeTicketRequest.Scene.builder().sceneStr(sceneStr).build()).build());
            String response = HttpUtil.post(String.format("%s/cgi-bin/qrcode/create?access_token=%s",
                            wxConfiguration.getWxApiHost(), getAccessToken()),
                    Collections.emptyMap(), JsonUtil.toJson(getQrCodeTicketRequest));
            GetQrCodeTicketResponse getQrCodeTicketResponse = JsonUtil.fromJson(response, GetQrCodeTicketResponse.class);
            return String.format("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s", getQrCodeTicketResponse.getTicket());
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String getLoginQrCode(String loginRandomId) {
        String sceneStr = String.format(SceneEnum.LOGIN.getFormatter(), loginRandomId);
        try {
            GetQrCodeTicketRequest getQrCodeTicketRequest = new GetQrCodeTicketRequest();
            getQrCodeTicketRequest.setExpireSeconds(720);
            getQrCodeTicketRequest.setActionName(GetQrCodeTicketRequest.ActionName.QR_STR_SCENE);
            getQrCodeTicketRequest.setActionInfo(GetQrCodeTicketRequest
                    .ActionInfo.builder().scene(GetQrCodeTicketRequest.Scene.builder().sceneStr(sceneStr).build()).build());
            String response = HttpUtil.post(String.format("%s/cgi-bin/qrcode/create?access_token=%s",
                            wxConfiguration.getWxApiHost(), getAccessToken()),
                    Collections.emptyMap(), JsonUtil.toJson(getQrCodeTicketRequest));
            GetQrCodeTicketResponse getQrCodeTicketResponse = JsonUtil.fromJson(response, GetQrCodeTicketResponse.class);
            return String.format("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s", getQrCodeTicketResponse.getTicket());
        } catch (Exception e) {
            log.error("getLoginQrCode error {}", loginRandomId, e);
            return "";
        }
    }

    @Override
    public Long sendTemplateMessage(SendTemplateMessageRequest sendTemplateMessageRequest) throws NeedSubscribeException {
        String response = HttpUtil.post(String.format("%s/cgi-bin/message/template/send?access_token=%s",
                        wxConfiguration.getWxApiHost(), getAccessToken()), Collections.emptyMap(),
                JsonUtil.toJson(sendTemplateMessageRequest));
        SendTemplateResponse sendTemplateResponse = JsonUtil.fromJson(response, SendTemplateResponse.class);
        if (SendTemplateErrorCode.REQUIRE_SUBSCRIBE.getCode() == sendTemplateResponse.getErrcode() ||
                SendTemplateErrorCode.INVALID_OPEN_ID.getCode() == sendTemplateResponse.getErrcode()) {
            throw new NeedSubscribeException("当前账号尚未绑定微信或者已取消关注，请重新扫码关注");
        }
        PreCondition.isTrue(sendTemplateResponse.getErrcode().equals(0), String.format("发送模板失败 %s", sendTemplateResponse));
        return sendTemplateResponse.getMsgId();
    }
}
