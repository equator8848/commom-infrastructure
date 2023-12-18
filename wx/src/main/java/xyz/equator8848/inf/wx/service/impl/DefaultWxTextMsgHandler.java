package xyz.equator8848.inf.wx.service.impl;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import xyz.equator8848.inf.wx.model.message.WxTextMessage;
import xyz.equator8848.inf.wx.service.WxTextHandler;

import java.util.Optional;

import static xyz.equator8848.inf.wx.model.message.WxTextMessage.buildTextResponse;

/**
 * Order 值越小，优先级越高。默认优先级是最低的
 */
@Order
@Service
public class DefaultWxTextMsgHandler extends AbsWxTextHandler implements WxTextHandler {
    @Override
    public boolean canHandle(WxTextMessage wxTextMessage) {
        return true;
    }

    @Override
    public String handle(WxTextMessage wxTextMessage) {
        return buildTextResponse(wxConfiguration.getOriginalId(), wxTextMessage.getFromUserName(),
                Optional.ofNullable(wxConfiguration.getDefaultWelcomeTips()).orElse("你好"));
    }
}
