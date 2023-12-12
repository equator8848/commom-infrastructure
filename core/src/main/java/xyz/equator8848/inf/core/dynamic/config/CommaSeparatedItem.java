package xyz.equator8848.inf.core.dynamic.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Equator
 * @Date: 2022/8/29 14:46
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommaSeparatedItem {
    Class collectionType();

    Class itemType();
}
