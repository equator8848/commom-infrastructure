package xyz.equator8848.inf.cache.redis.config;


import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import xyz.equator8848.inf.core.util.json.JsonUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 自定义序列化器
 */
public class JackJson2JsonRedisSerializer<T> implements RedisSerializer<T> {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final Class<T> clazz;

    public JackJson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        String json = JsonUtil.toJson(t);
        if (json == null) {
            throw new SerializationException("JsonUtil.toJson failed");
        }
        return json.getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return JsonUtil.fromJson(new String(bytes, DEFAULT_CHARSET), clazz);
    }
}
