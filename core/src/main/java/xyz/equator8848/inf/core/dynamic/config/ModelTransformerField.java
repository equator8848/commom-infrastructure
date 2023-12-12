package xyz.equator8848.inf.core.dynamic.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Equator
 * @Date: 2022/8/17 20:21
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelTransformerField {
    /**
     * @return
     */
    String value() default "";

    /**
     * 是否允许为空
     *
     * @return
     */
    boolean nullable() default false;

    /**
     * 默认值
     *
     * @return
     */
    String defaultValue() default "";
}
