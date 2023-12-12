package xyz.equator8848.inf.cache.common;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Equator
 * @Date: 2022/1/8 9:39
 **/

public interface VersionCacheLoader<K, V, D> extends SimpleCacheLoader<K, D> {
    /**
     * 加载版本
     *
     * @param key
     * @param data
     * @return
     */
    V loadVersion(K key, D data);

    /**
     * 加载成功回调
     *
     * @param key
     */
    void loadVersionSuccess(K key);

    /**
     * 加载失败回调
     *
     * @param key
     * @param exception
     */
    void loadVersionFail(K key, Exception exception);

    /**
     * 加载耗时回调
     *
     * @param key
     * @param cost
     * @param timeUnit
     */
    void loadVersionCost(K key, long cost, TimeUnit timeUnit);
}
