package xyz.equator8848.inf.core.util.json;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import xyz.equator8848.inf.core.model.exception.InnerException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 非驼峰字段无法序列化 https://zhuanlan.zhihu.com/p/628668559
 * @Author: Equator
 * @Date: 2020/11/1 22:01
 **/
@Slf4j
public class JsonUtil {
    private final static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
    }

    /**
     * 反序列化为对象
     *
     * @param str
     * @param classOfT
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String str, Class<T> classOfT) {
        if (str == null || "".equals(str)) {
            try {
                return classOfT.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        try {
            return mapper.readValue(str, classOfT);
        } catch (IOException e) {
            log.error("from json error, {}", str, e);
            throw new InnerException("fromJson error");
        }
    }

    public static <T> T fromJson(String str, TypeReference<T> valueTypeRef) {
        if (str == null || "".equals(str)) {
            try {
                // TODO FIX IT
                return (T) Class.forName(valueTypeRef.getType().getTypeName()).newInstance();
            } catch (Exception e) {
                throw new InnerException("fromJson error");
            }
        }
        try {
            return mapper.readValue(str, valueTypeRef);
        } catch (IOException e) {
            throw new InnerException("fromJson error");
        }
    }


    public static <T> T fromJson(String str, Type type) {
        Class<?> clazz = com.fasterxml.jackson.databind.type.TypeFactory.rawClass(type);
        try {
            return (T) mapper.readValue(str, clazz);
        } catch (IOException e) {
            log.error("from json error, {}", str, e);
            throw new InnerException("fromJson error");
        }
    }

    public static <T> T fromJson(JsonNode jsonNode, Class<T> classOfT) {
        if (jsonNode == null) {
            return null;
        }
        return mapper.convertValue(jsonNode, classOfT);
    }

    /**
     * 反序列化为列表
     *
     * @param str
     * @param classOfT
     * @param <T>
     * @return
     */
    public static <T> List<T> fromJsonList(String str, Class<T> classOfT) {
        if (str == null) {
            return null;
        }
        try {
            return mapper.readValue(str, new TypeReference<List<T>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 序列化为Json
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 序列化为Json
     *
     * @param obj
     * @return
     */
    public static String toJson(JsonNode obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isEmpty(JsonNode jsonNode, String memberName) {
        JsonNode value = jsonNode.findValue(memberName);
        return value == null || value.isNull();
    }

    /**
     * 将字符串解析为json对象
     *
     * @param str
     * @return
     */
    public static JsonNode toJsonObject(String str) {
        try {
            return mapper.readTree(str);
        } catch (IOException e) {
            throw new InnerException("toJsonObject error");
        }
    }

    /**
     * 获取对象
     *
     * @return
     */
    public static ObjectNode getJsonObject() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.createObjectNode();
    }

    private void testInt() {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        String json = JsonUtil.toJson(integers);
        System.out.println(json);
        List<Integer> list = JsonUtil.fromJsonList(json, Integer.class);
        System.out.println(list);
    }

    private void testString() {
        List<String> strings = new ArrayList<>();
        strings.add("leo");
        strings.add("hello");
        strings.add("hi");
        String json = JsonUtil.toJson(strings);
        System.out.println(json);
        List<String> list = JsonUtil.fromJsonList(json, String.class);
        System.out.println(list);
    }
}
