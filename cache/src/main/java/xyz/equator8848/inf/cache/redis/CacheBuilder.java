package xyz.equator8848.inf.cache.redis;


import xyz.equator8848.inf.cache.redis.service.RedisService;

/**
 * @author
 * @date 2023/10/9 10:14
 */
public class CacheBuilder<K, V> {

    public static CacheBuilder<Object, Object> newBuilder() {
        return new CacheBuilder();
    }

    @SuppressWarnings("unchecked")
    public <K1 extends K, V1 extends V> CacheHolder<K1, V1> build(CacheLoader<? super K1, V1> loader, RedisService redisService) {
        return new RedisCacheHolder(this, loader, redisService);
    }
}
