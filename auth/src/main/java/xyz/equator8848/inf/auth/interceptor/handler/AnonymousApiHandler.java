package xyz.equator8848.inf.auth.interceptor.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import xyz.equator8848.inf.auth.annotation.AnonymousApi;
import xyz.equator8848.inf.auth.model.bo.LoginUser;

import java.util.Objects;

@Component
public class AnonymousApiHandler implements ApiPermissionHandler {
    @Override
    public boolean canHandle(HandlerMethod handlerMethod) {
        return Objects.nonNull(handlerMethod.getBeanType().getAnnotation(AnonymousApi.class)) ||
                Objects.nonNull(handlerMethod.getMethodAnnotation(AnonymousApi.class));
    }

    @Override
    public LoginUser buildLoginUser(String token) {
        return null;
    }

    @Override
    public boolean permissionValidate(HandlerMethod handlerMethod, String token) {
        return true;
    }
}
