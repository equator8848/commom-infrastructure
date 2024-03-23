package xyz.equator8848.inf.auth.interceptor.handler;

import org.springframework.web.method.HandlerMethod;
import xyz.equator8848.inf.auth.model.bo.LoginUser;

public interface ApiPermissionHandler {
    boolean canHandle(HandlerMethod handlerMethod);

    LoginUser buildLoginUser(String token);

    boolean permissionValidate(HandlerMethod handlerMethod, String token);
}
