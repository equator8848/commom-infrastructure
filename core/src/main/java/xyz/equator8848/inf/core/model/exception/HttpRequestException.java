package xyz.equator8848.inf.core.model.exception;

import lombok.Data;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author: Equator
 * @Date: 2020/11/1 22:24
 **/
@Data
public class HttpRequestException extends RuntimeException {
    private Request request;
    private Response response;

    public HttpRequestException(Request request, Response response) {
        this.request = request;
        this.response = response;
    }
}
