package xyz.equator8848.inf.auth.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 将此注解加到类上，这个类所有的方法都不校验接口权限
 * 将此注解加到方法上，该方法不校验接口权限
 *
 * @author equator
 */

@Component
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotValidatePermissionApi {

}