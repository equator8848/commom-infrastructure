package xyz.equator8848.inf.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 极简RBAC 权限控制API
 *
 * @Author: Equator
 * @Date: 2021/2/10 10:45
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SimpleRBACApi {
    int requireRoleType();
}
