package xyz.equator8848.inf.core.http;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;

/**
 * @Author: Equator
 * @Date: 2022/9/30 18:11
 **/
@Slf4j
public class MediaUtil {
    public final static MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    public final static MediaType XML = MediaType.parse("application/xml;charset=utf-8");

    public final static MediaType OCTET_STREAM = MediaType.parse("application/octet-stream");

    public final static MediaType MULTIPART = MediaType.parse("multipart/form-data;charset=utf-8");

    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
}
