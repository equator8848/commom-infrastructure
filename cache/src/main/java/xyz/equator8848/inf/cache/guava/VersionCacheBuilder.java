package xyz.equator8848.inf.cache.guava;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import xyz.equator8848.inf.cache.common.VersionCacheElement;
import xyz.equator8848.inf.cache.common.VersionCacheLoader;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Equator
 * @Date: 2022/1/8 10:11
 **/

public class VersionCacheBuilder extends AbstractCacheBuilder {

    public static VersionCacheBuilder newBuilder() {
        return new VersionCacheBuilder();
    }

    @Override
    public VersionCacheBuilder maximumSize(long maximumSize) {
        return (VersionCacheBuilder) super.maximumSize(maximumSize);
    }

    @Override
    public VersionCacheBuilder expireAfterAccess(long duration, TimeUnit unit) {
        return (VersionCacheBuilder) super.expireAfterAccess(duration, unit);
    }

    @Override
    public VersionCacheBuilder expireAfterWrite(long duration, TimeUnit unit) {
        return (VersionCacheBuilder) super.expireAfterWrite(duration, unit);
    }

    @Override
    public VersionCacheBuilder refreshAfterWrite(long duration, TimeUnit unit) {
        return (VersionCacheBuilder) super.refreshAfterWrite(duration, unit);
    }

    public <K, V, D> LoadingCache<K, VersionCacheElement<V, D>> build(final VersionCacheLoader<K, V, D> cacheLoader, final Executor executor) {
        return cacheBuilder.build(CacheLoader.asyncReloading(getCacheLoader(cacheLoader), executor));
    }

    public <K, V, D> LoadingCache<K, VersionCacheElement<V, D>> build(final VersionCacheLoader<K, V, D> cacheLoader) {
        return cacheBuilder.build(getCacheLoader(cacheLoader));
    }

    private <K, V, D> CacheLoader<K, VersionCacheElement<V, D>> getCacheLoader(final VersionCacheLoader<K, V, D> cacheLoader) {
        return new CacheLoader<>() {
            @Override
            public VersionCacheElement<V, D> load(K key) throws Exception {
                return load0(key);
            }

            @Override
            public ListenableFuture<VersionCacheElement<V, D>> reload(K key, VersionCacheElement<V, D> oldValue) throws Exception {
                return Futures.immediateFuture(reload0(key, oldValue));
            }

            private VersionCacheElement<V, D> load0(K key) {
                long startTime = System.nanoTime();
                D data;
                try {
                    data = cacheLoader.loadData(key);
                    cacheLoader.loadDataSuccess(key);
                } catch (Exception e) {
                    cacheLoader.loadDataFail(key, e);
                    return new VersionCacheElement<>(e);
                } finally {
                    cacheLoader.loadDataCost(key, System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
                }
                return loadVersion(key, data);
            }

            private VersionCacheElement<V, D> reload0(K key, VersionCacheElement<V, D> oldValue) {
                if (oldValue.isException()) {
                    return load0(key);
                }

                VersionCacheElement<V, D> newValue = loadVersion(key, oldValue.getData());
                if (newValue.isException()) {
                    return getData(newValue, oldValue);
                }

                if (newValue.getVersion().equals(oldValue.getVersion())) {
                    return oldValue;
                }

                return getData(load0(key), oldValue);
            }

            private VersionCacheElement<V, D> loadVersion(K key, D value) {
                long startTime = System.nanoTime();
                try {
                    V version = cacheLoader.loadVersion(key, value);
                    cacheLoader.loadVersionSuccess(key);
                    return new VersionCacheElement<>(version, value);
                } catch (Exception e) {
                    cacheLoader.loadVersionFail(key, e);
                    return new VersionCacheElement<>(e);
                } finally {
                    cacheLoader.loadVersionCost(key, System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
                }
            }
        };
    }
}
