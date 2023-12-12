package xyz.equator8848.inf.cache.common;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Equator
 * @Date: 2022/1/8 9:39
 **/

public interface SimpleCacheLoader<K, D> {
    default String getStringKey(K key) {
        return key.toString();
    }

    /**
     * 加载数据
     *
     * @param key
     * @return
     * @throws Exception
     */
    D loadData(K key) throws Exception;

    /**
     * 加载成功回调
     *
     * @param key
     */
    void loadDataSuccess(K key);

    /**
     * 加载失败回调
     *
     * @param key
     * @param exception
     */
    void loadDataFail(K key, Exception exception);

    /**
     * 加载耗时回调
     *
     * @param key
     * @param cost
     * @param timeUnit
     */
    void loadDataCost(K key, long cost, TimeUnit timeUnit);
}
