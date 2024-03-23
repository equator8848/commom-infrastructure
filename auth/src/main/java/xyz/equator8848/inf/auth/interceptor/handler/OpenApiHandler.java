package xyz.equator8848.inf.auth.interceptor.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import xyz.equator8848.inf.auth.annotation.OpenApi;
import xyz.equator8848.inf.auth.model.bo.LoginUser;
import xyz.equator8848.inf.core.dynamic.properties.AuthConfig;
import xyz.equator8848.inf.core.model.constant.SeparatorConstant;
import xyz.equator8848.inf.core.model.exception.PreCondition;

import java.util.Objects;
import java.util.Set;

@Component
public class OpenApiHandler implements ApiPermissionHandler {
    @Autowired
    private AuthConfig authConfig;

    @Override
    public boolean canHandle(HandlerMethod handlerMethod) {
        return Objects.nonNull(handlerMethod.getBeanType().getAnnotation(OpenApi.class)) ||
                Objects.nonNull(handlerMethod.getMethodAnnotation(OpenApi.class));
    }

    @Override
    public LoginUser buildLoginUser(String token) {
        String[] splitTokenArr = token.split(SeparatorConstant.SEPARATOR_COLON);
        PreCondition.isTrue(splitTokenArr.length == 2, "OpenApi格式不合法");
        return LoginUser.buildSystemUser(splitTokenArr[0]);
    }

    @Override
    public boolean permissionValidate(HandlerMethod handlerMethod, String token) {
        Set<String> openApiTokens = authConfig.getOpenApiTokens();
        if (CollectionUtils.isEmpty(openApiTokens)) {
            return false;
        }
        return openApiTokens.contains(token);
    }
}
