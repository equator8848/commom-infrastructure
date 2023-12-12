package xyz.equator8848.inf.cache.redis;


import xyz.equator8848.inf.cache.common.SimpleCacheElement;

/**
 * @author Equator
 **/

public abstract class AbstractCacheBuilder {
    protected final CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();

    /**
     * 获取新值异常时，返回旧值
     *
     * @param newData
     * @param oldData
     * @param <D>
     * @return
     */
    protected <D extends SimpleCacheElement<?>> D getData(D newData, D oldData) {
        if (newData.isException()) {
            return oldData;
        }
        return newData;
    }
}
