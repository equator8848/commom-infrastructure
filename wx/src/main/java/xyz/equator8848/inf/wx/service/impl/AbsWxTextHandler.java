package xyz.equator8848.inf.wx.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.equator8848.inf.wx.model.props.WxConfiguration;

@Service
public class AbsWxTextHandler {
    @Autowired
    protected WxConfiguration wxConfiguration;
}
