package xyz.equator8848.inf.cache.guava;


import xyz.equator8848.inf.cache.common.SimpleCacheElement;
import xyz.equator8848.inf.cache.common.SimpleCacheLoader;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Equator
 * @Date: 2022/1/8 10:11
 **/

public class SimpleCacheBuilder extends AbstractCacheBuilder {

    public static SimpleCacheBuilder newBuilder() {
        return new SimpleCacheBuilder();
    }

    @Override
    public SimpleCacheBuilder maximumSize(long maximumSize) {
        return (SimpleCacheBuilder) super.maximumSize(maximumSize);
    }

    @Override
    public SimpleCacheBuilder expireAfterAccess(long duration, TimeUnit unit) {
        return (SimpleCacheBuilder) super.expireAfterAccess(duration, unit);
    }

    @Override
    public SimpleCacheBuilder expireAfterWrite(long duration, TimeUnit unit) {
        return (SimpleCacheBuilder) super.expireAfterWrite(duration, unit);
    }

    @Override
    public SimpleCacheBuilder refreshAfterWrite(long duration, TimeUnit unit) {
        return (SimpleCacheBuilder) super.refreshAfterWrite(duration, unit);
    }

    /**
     * 异步
     *
     * @param cacheLoader
     * @param executor
     * @param <K>
     * @param <D>
     * @return
     */
    public <K, D> LoadingCache<K, SimpleCacheElement<D>> build(final SimpleCacheLoader<K, D> cacheLoader,
                                                               final Executor executor) {
        return cacheBuilder.build(CacheLoader.asyncReloading(
                new CacheLoader<>() {
                    @Override
                    public SimpleCacheElement<D> load(K key) throws Exception {
                        return load0(key);
                    }

                    @Override
                    public ListenableFuture<SimpleCacheElement<D>> reload(K key, SimpleCacheElement<D> oldValue) throws Exception {
                        return Futures.immediateFuture(reload0(key, oldValue));
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
                },
                executor));
    }
}
