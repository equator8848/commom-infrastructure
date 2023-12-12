package xyz.equator8848.inf.cache.guava;

import xyz.equator8848.inf.cache.common.SimpleCacheElement;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Equator
 * @Date: 2022/1/8 10:04
 **/

public abstract class AbstractCacheBuilder {
    protected final CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();

    public AbstractCacheBuilder maximumSize(long maximumSize) {
        cacheBuilder.maximumSize(maximumSize);
        return this;
    }

    public AbstractCacheBuilder expireAfterAccess(long duration, TimeUnit unit) {
        cacheBuilder.expireAfterAccess(duration, unit);
        return this;
    }

    public AbstractCacheBuilder expireAfterWrite(long duration, TimeUnit unit) {
        cacheBuilder.expireAfterWrite(duration, unit);
        return this;
    }

    public AbstractCacheBuilder refreshAfterWrite(long duration, TimeUnit unit) {
        cacheBuilder.refreshAfterWrite(duration, unit);
        return this;
    }

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
