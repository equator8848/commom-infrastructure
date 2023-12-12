package xyz.equator8848.inf.auth.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 平台管理员使用的接口，即系统开发者使用的接口
 *
 * @author equator
 */

@Component
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminApi {

}