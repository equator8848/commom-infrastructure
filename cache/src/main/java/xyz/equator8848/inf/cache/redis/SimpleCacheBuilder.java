package xyz.equator8848.inf.cache.redis;


import xyz.equator8848.inf.cache.common.SimpleCacheElement;
import xyz.equator8848.inf.cache.common.SimpleCacheLoader;
import xyz.equator8848.inf.cache.redis.service.RedisService;

import java.util.concurrent.TimeUnit;

/**
 * @author
 * @date 2023/10/9 10:10
 */
public class SimpleCacheBuilder extends AbstractCacheBuilder {
    public static SimpleCacheBuilder newBuilder() {
        return new SimpleCacheBuilder();
    }

    public <K, D> CacheHolder<K, SimpleCacheElement<D>> build(final SimpleCacheLoader<K, D> cacheLoader,
                                                              final RedisService redisService) {
        return cacheBuilder.build(new CacheLoader<>() {
            @Override
            public SimpleCacheElement<D> load(K key) {
                return load0(key);
            }

            @Override
            public SimpleCacheElement<D> reload(K key, SimpleCacheElement<D> oldValue) {
                return reload0(key, oldValue);
            }

            @Override
            public String getStringKey(K key) {
                return cacheLoader.getStringKey(key);
            }

            private SimpleCacheElement<D> load0(K key) {
                long startTime = System.nanoTime();
                try {
                    D data = cacheLoader.loadData(key);
                    cacheLoader.loadDataSuccess(key);
                    return new SimpleCacheElement<>(data);
                } catch (Exception e) {
                    cacheLoader.loadDataFail(key, e);
                    return new SimpleCacheElement<>(e);
                } finally {
                    cacheLoader.loadDataCost(key, System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
                }
            }

            private SimpleCacheElement<D> reload0(K key, SimpleCacheElement<D> oldData) {
                return getData(load0(key), oldData);
            }
        }, redisService);
    }
}
