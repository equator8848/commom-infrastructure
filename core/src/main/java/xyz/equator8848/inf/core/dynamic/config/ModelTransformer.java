package xyz.equator8848.inf.core.dynamic.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import xyz.equator8848.inf.core.model.exception.InnerException;
import xyz.equator8848.inf.core.model.exception.VerifyException;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author: Equator
 * @Date: 2022/8/17 20:15
 **/
@Slf4j

public class ModelTransformer {
    /**
     * map转对象
     *
     * @param map
     * @param target
     */
    public static void mapToObject(Map<String, Object> map, Object target) {
        Class<?> targetClass = target.getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();
        for (Field field : declaredFields) {
            ModelTransformerField modelTransformerField = field.getAnnotation(ModelTransformerField.class);
            String key;
            if (modelTransformerField == null) {
                key = field.getName();
            } else {
                key = modelTransformerField.value();
            }
            field.setAccessible(true);
            log.debug("{} {}", field.getName(), field.getType().getTypeName());
            Object value = map.getOrDefault(key, null);
            if (value == null && modelTransformerField != null) {
                value = map.getOrDefault(field.getName(), null);
            }
            try {
                field.set(target, value);
            } catch (IllegalAccessException e) {
                log.error("mapToObject set value failed, name: {}, type: {}, value: {}", key, field.getType(), value);
            }
        }
    }

    /**
     * 字符串map转对象
     *
     * @param map
     * @param target
     */
    public static void stringMapToObject(Map<String, String> map, Object target) {
        Class<?> targetClass = target.getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();
        for (Field field : declaredFields) {
            ModelTransformerField modelTransformerField = field.getAnnotation(ModelTransformerField.class);
            String key;
            if (modelTransformerField == null || StringUtils.isEmpty(modelTransformerField.value())) {
                key = field.getName();
            } else {
                key = modelTransformerField.value();
            }
            field.setAccessible(true);
            // log.debug("{} {}", field.getName(), field.getType());
            String value = map.getOrDefault(key, null);
            if (value == null && modelTransformerField != null) {
                value = map.getOrDefault(field.getName(), null);
            }
            try {
                if (value == null) {
                    if (modelTransformerField != null) {
                        if (!StringUtils.isEmpty(modelTransformerField.defaultValue())) {
                            value = modelTransformerField.defaultValue();
                        } else if (modelTransformerField.nullable()) {
                            continue;
                        } else {
                            throw new VerifyException(String.format("%s value can not null", field.getName()));
                        }
                    } else {
                        throw new VerifyException(String.format("%s value can not null", field.getName()));
                    }
                }
                CommaSeparatedItem commaSeparatedItem = field.getAnnotation(CommaSeparatedItem.class);
                if (commaSeparatedItem == null) {
                    // 普通值
                    field.set(target, transformWithType(value, field));
                } else {
                    // 集合
                    Class collectionType = commaSeparatedItem.collectionType();
                    String[] splitItems = value.split(",");
                    if (splitItems.length == 0) {
                        throw new VerifyException("collection can not empty");
                    }
                    Class itemType = commaSeparatedItem.itemType();
                    if (collectionType.equals(List.class)) {
                        List<Object> list = new ArrayList<>(splitItems.length);
                        for (String splitItem : splitItems) {
                            list.add(transformWithType(splitItem, itemType.getTypeName()));
                        }
                        field.set(target, list);
                    } else if (collectionType.equals(Set.class)) {
                        Set<Object> set = new HashSet<>(splitItems.length);
                        for (String splitItem : splitItems) {
                            set.add(transformWithType(splitItem, itemType.getTypeName()));
                        }
                        field.set(target, set);
                    }
                }
            } catch (IllegalAccessException e) {
                log.error("mapToObject set value failed, name: {}, type: {}, value: {}", key, field.getType(), value);
            }
        }

    }

    /**
     * 根据类型信息，将字符串转换为对应类型
     *
     * @param value
     * @param field
     * @return
     */
    private static Object transformWithType(String value, Field field) {
        return transformWithType(value, field.getType().getTypeName());
    }


    private static Object transformWithType(String value, String typeName) {
        switch (typeName) {
            case "java.lang.String":
                return value;
            case "int":
            case "java.lang.Integer":
                return Integer.valueOf(value);
            case "long":
            case "java.lang.Long":
                return Long.valueOf(value);
            case "double":
            case "java.lang.Double":
                return Double.valueOf(value);
            case "boolean":
            case "java.lang.Boolean":
                return Boolean.valueOf(value);
            case "short":
            case "java.lang.Short":
                return Short.valueOf(value);
            case "float":
            case "java.lang.Float":
                return Float.valueOf(value);
            case "byte":
            case "java.lang.Byte":
                return Byte.valueOf(value);
            default:
                throw new InnerException("不支持的数据类型");
        }
    }
}
