package xyz.equator8848.inf.cache.redis;


import xyz.equator8848.inf.cache.redis.service.RedisService;

import java.util.concurrent.TimeUnit;

/**
 * @author Equator
 **/
public class RedisCacheHolder<K, V> implements CacheHolder<K, V> {
    private final CacheLoader<K, V> cacheLoader;

    private final CacheBuilder<K, V> cacheBuilder;

    private final RedisService redisService;

    public RedisCacheHolder(CacheBuilder<K, V> cacheBuilder, CacheLoader<K, V> cacheLoader, RedisService redisService) {
        this.cacheBuilder = cacheBuilder;
        this.cacheLoader = cacheLoader;

        this.redisService = redisService;
    }

    @Override
    public V get(K key, long expireSecond) {
        String stringKey = cacheLoader.getStringKey(key);
        V cacheObject = redisService.getCacheObject(stringKey);
        if (cacheObject != null) {
            return cacheObject;
        }
        try {
            cacheObject = cacheLoader.load(key);
            redisService.setCacheObject(stringKey, cacheObject, expireSecond, TimeUnit.SECONDS);
            return cacheObject;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public V compareAndGet(K key, long expireSecond) {
        String stringKey = cacheLoader.getStringKey(key);
        V cacheObject = redisService.getCacheObject(stringKey);
        if (cacheLoader.isVersionChange(key, cacheObject)) {
            try {
                cacheObject = cacheLoader.load(key);
            } catch (Exception e) {
                return null;
            }
            redisService.setCacheObject(stringKey, cacheObject, expireSecond, TimeUnit.SECONDS);
            return cacheObject;
        } else {
            return cacheObject;
        }
    }

    @Override
    public void refresh(K key, long expireSecond) {
        redisService.expire(cacheLoader.getStringKey(key), expireSecond);
    }

    @Override
    public void remove(K key) {
        redisService.deleteObject(cacheLoader.getStringKey(key));
    }
}
