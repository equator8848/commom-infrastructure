package xyz.equator8848.inf.auth.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * value是权限code的集合
 * 将此注解加到类上，或者方法上均可
 * value中的code在用户拥有的权限中的话，通过鉴权
 *
 * @author equator
 */

@Component
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatePermissionApi {
    /**
     * 权限码的集合
     *
     * @return
     */
    String[] value();
}