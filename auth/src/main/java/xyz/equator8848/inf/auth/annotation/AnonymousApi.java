package xyz.equator8848.inf.auth.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 匿名访问接口
 *
 * @author equator
 */

@Component
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnonymousApi {

}