package xyz.equator8848.inf.auth.interceptor.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import xyz.equator8848.inf.auth.annotation.SimpleRBACApi;
import xyz.equator8848.inf.auth.model.bo.LoginUser;
import xyz.equator8848.inf.auth.model.constant.RoleType;
import xyz.equator8848.inf.auth.util.UserAuthUtil;

import java.util.Objects;
import java.util.Optional;

@Component
public class SimpleRBACApiHandler implements ApiPermissionHandler {
    @Autowired
    private UserAuthUtil userAuthUtil;

    @Override
    public boolean canHandle(HandlerMethod handlerMethod) {
        return Objects.nonNull(handlerMethod.getBeanType().getAnnotation(SimpleRBACApi.class)) ||
                Objects.nonNull(handlerMethod.getMethodAnnotation(SimpleRBACApi.class));
    }

    @Override
    public LoginUser buildLoginUser(String token) {
        return userAuthUtil.getLoginUserFromJWT(token);
    }

    @Override
    public boolean permissionValidate(HandlerMethod handlerMethod, String token) {
        SimpleRBACApi classAnnotation = handlerMethod.getBeanType().getAnnotation(SimpleRBACApi.class);

        // 默认是访客权限
        int requireRoleType = RoleType.VISITOR;
        if (Objects.nonNull(classAnnotation)) {
            requireRoleType = classAnnotation.requireRoleType();
        }

        SimpleRBACApi methodAnnotation = handlerMethod.getMethodAnnotation(SimpleRBACApi.class);
        if (Objects.nonNull(methodAnnotation)) {
            requireRoleType = methodAnnotation.requireRoleType();
        }

        if (requireRoleType == RoleType.VISITOR) {
            return true;
        }

        LoginUser loginUser = buildLoginUser(token);
        if (loginUser == null) {
            return false;
        }

        int userRoleType = Optional.ofNullable(loginUser.getRoleType()).orElse(RoleType.VISITOR).intValue();
        return userRoleType >= requireRoleType;
    }
}
