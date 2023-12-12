package xyz.equator8848.inf.cache.common;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Equator
 * @Date: 2022/1/8 9:45
 **/
@Slf4j
public abstract class LogSimpleCacheLoader<K, D> implements SimpleCacheLoader<K, D> {
    /**
     * 获取缓存名称
     *
     * @return
     */
    public abstract String getCacheName();

    /**
     * 获取key
     *
     * @return
     */
    public String getCacheKey(K key) {
        return key.toString();
    }


    @Override
    public void loadDataSuccess(K key) {
        log.debug("{} loadDataSuccess, key {}", getCacheName(), getCacheKey(key));
    }

    @Override
    public void loadDataFail(K key, Exception exception) {
        log.error("{} loadDataFail, key {}", getCacheName(), getCacheKey(key), exception);
    }

    @Override
    public void loadDataCost(K key, long cost, TimeUnit timeUnit) {
        log.debug("{} load {} cost {} ms", getCacheName(), getCacheKey(key), timeUnit.toMillis(cost));
    }
}
