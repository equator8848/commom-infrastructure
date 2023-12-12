package xyz.equator8848.inf.cache.common;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Equator
 * @Date: 2022/1/8 9:45
 **/
@Slf4j
public abstract class LogVersionCacheLoader<K, V, D> extends LogSimpleCacheLoader<K, D> implements VersionCacheLoader<K, V, D> {

    @Override
    public void loadVersionSuccess(K key) {
        log.debug("{} loadVersionSuccess, key {}", getCacheName(), getCacheKey(key));
    }

    @Override
    public void loadVersionFail(K key, Exception exception) {
        log.error("{} loadVersionFail, key {}", getCacheName(), getCacheKey(key), exception);
    }

    @Override
    public void loadVersionCost(K key, long cost, TimeUnit timeUnit) {
        log.debug("{} load version {} cost {} ms", getCacheName(), getCacheKey(key), timeUnit.toMillis(cost));
    }
}
