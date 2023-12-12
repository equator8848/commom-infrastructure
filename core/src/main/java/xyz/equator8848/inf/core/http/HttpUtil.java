package xyz.equator8848.inf.core.http;

import xyz.equator8848.inf.core.model.exception.HttpRequestException;
import xyz.equator8848.inf.core.model.exception.VerifyException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

/**
 * @Author: Equator
 * @Date: 2020/11/1 22:01
 **/
@Slf4j
public final class HttpUtil {

    public static class StatusCode {
        public static final String OK_CODE = "0000";
    }

    public final static MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    public final static MediaType XML = MediaType.parse("application/xml;charset=utf-8");

    public final static MediaType OCTET_STREAM = MediaType.parse("application/octet-stream");

    public final static MediaType MULTIPART = MediaType.parse("multipart/form-data;charset=utf-8");

    public static final MediaType FORM_CONTENT_TYPE = MediaType.parse("application/x-www-form-urlencoded;" +
            "charset=utf-8");

    /**
     * GET
     */
    public static String get(String url, Map<String, String> headers, Map<String, Object> params) {
        Request request = new Request.Builder().url(buildUrl(url, params)).headers(Headers.of(headers)).build();
        return doRequestGetBody(request);
    }

    public static String get(String url, Map<String, String> headers) {
        Request request = new Request.Builder().url(url).headers(Headers.of(headers)).build();
        return doRequestGetBody(request);
    }

    public static String get(String url) {
        return get(url, Collections.emptyMap(), null);
    }

    public static InputStream getFile(String url) {
        return getFile(url, null);
    }

    public static InputStream getFile(String url, Headers headers) {
        Request request = null;
        if (headers == null) {
            request = new Request.Builder().url(url).build();
        } else {
            request = new Request.Builder().url(url).headers(headers).build();
        }
        Response response = null;
        try {
            response = new OkHttpClient().newCall(request).execute();
            if (response.code() == HttpStatus.OK.value()) {
                return response.body().byteStream();
            } else if (response.code() == HttpStatus.NOT_FOUND.value()) {
                throw new VerifyException("找不到文件");
            }
        } catch (IOException e) {
            throw new HttpRequestException(request, response);
        }
        throw new HttpRequestException(request, response);
    }

    public static String post(String url, Map<String, String> headers, Map<String, Object> params, String bodyContent) {
        RequestBody requestBody = RequestBody.create(JSON, bodyContent);
        Request request =
                new Request.Builder().url(buildUrl(url, params)).headers(Headers.of(headers)).post(requestBody).build();
        return doRequestGetBody(request);
    }

    public static String post(String url, Map<String, String> headers, String bodyContent) {
        RequestBody requestBody = RequestBody.create(JSON, bodyContent);
        Request request =
                new Request.Builder().url(buildUrl(url, Collections.emptyMap())).headers(Headers.of(headers)).post(requestBody).build();
        return doRequestGetBody(request);
    }

    public static String put(String url, Map<String, String> headers, Map<String, Object> params, String bodyContent) {
        RequestBody requestBody = RequestBody.create(JSON, bodyContent);
        Request request =
                new Request.Builder().url(buildUrl(url, params)).headers(Headers.of(headers)).put(requestBody).build();
        return doRequestGetBody(request);
    }

    public static String delete(String url, Map<String, String> headers, Map<String, Object> params,
                                String bodyContent) {
        RequestBody requestBody = RequestBody.create(JSON, bodyContent);
        Request request =
                new Request.Builder().url(buildUrl(url, params)).headers(Headers.of(headers)).delete(requestBody).build();
        return doRequestGetBody(request);
    }


    /**
     * 在URL中拼接参数
     *
     * @param url
     * @param params
     * @return
     */
    public static String buildUrl(String url, Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return url;
        }
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl == null) {
            return null;
        }
        HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (param.getValue() != null) {
                urlBuilder.addQueryParameter(param.getKey(), param.getValue().toString());
            } else {
                urlBuilder.addQueryParameter(param.getKey(), null);
            }
        }
        return urlBuilder.build().toString();
    }

    /**
     * 发起HTTP请求并提取响应中的Body数据
     *
     * @param request
     * @return
     */
    public static String doRequestGetBody(Request request) {
        Response response = null;
        String responseBody = null;
        try {
            response = new OkHttpClient().newCall(request).execute();
            responseBody = response.body().string();
            log.debug("http request url: {}, request: {}, response: {}", request.url(), request, responseBody);
            if (response.code() == HttpStatus.OK.value()) {
                return responseBody;
            }
        } catch (IOException e) {
            log.error("http request io exception {}", request, e);
            throw new HttpRequestException(request, response);
        }
        log.error("http request failed {} {}", request, responseBody);
        throw new HttpRequestException(request, response);
    }

    public static Response doRequestGetResponse(Request request) {
        Response response = null;
        try {
            response = new OkHttpClient().newCall(request).execute();
            if (response.code() == HttpStatus.OK.value()) {
                return response;
            }
        } catch (IOException e) {
            log.error("http request io exception {}", request, e);
            throw new HttpRequestException(request, response);
        }
        log.error("http request failed {} {}", request, response);
        throw new HttpRequestException(request, response);
    }

    private static String buildQuery(Map<String, Object> params) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://localhost/").newBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (param.getValue() != null) {
                urlBuilder.addQueryParameter(param.getKey(), param.getValue().toString());
            } else {
                urlBuilder.addQueryParameter(param.getKey(), null);
            }
        }
        return urlBuilder.build().query();
    }

    /**
     * 发起POST表单请求
     * 如果用FormBody 发送post表单请求，就不能设置编码格式，contentType方法没有暴露出来，默认也不是UTF-8，参数是汉字就会乱码；
     * 所以换一种方式 ，用   RequestBody
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPostFormData(String url, Map<String, Object> params) {
        RequestBody body = RequestBody.create(FORM_CONTENT_TYPE, buildQuery(params));
        Request request = new Request.Builder().url(url).post(body).build();
        return doRequestGetBody(request);
    }
}
