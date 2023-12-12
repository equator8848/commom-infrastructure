package xyz.equator8848.inf.cache.redis;



import xyz.equator8848.inf.cache.common.VersionCacheElement;
import xyz.equator8848.inf.cache.common.VersionCacheLoader;
import xyz.equator8848.inf.cache.redis.service.RedisService;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Equator
 * @Date: 2022/1/8 10:11
 **/

public class VersionCacheBuilder extends AbstractCacheBuilder {

    public static VersionCacheBuilder newBuilder() {
        return new VersionCacheBuilder();
    }

    public <K, V, D> CacheHolder<K, VersionCacheElement<V, D>> build(final VersionCacheLoader<K, V, D> cacheLoader,
                                                                     final RedisService redisService) {
        return cacheBuilder.build(new CacheLoader<>() {

            @Override
            public VersionCacheElement<V, D> load(K key) throws Exception {
                return load0(key);
            }

            @Override
            public VersionCacheElement<V, D> reload(K key, VersionCacheElement<V, D> oldValue) throws Exception {
                return reload0(key, oldValue);
            }

            @Override
            public boolean isVersionChange(K key, @Nullable VersionCacheElement<V, D> oldValue) {
                if (oldValue == null) {
                    return true;
                }
                // 最新版本
                V latestVersion = cacheLoader.loadVersion(key, oldValue.getData());
                if (latestVersion == null) {
                    return oldValue.getVersion() == null;
                }
                return !latestVersion.equals(oldValue.getVersion());
            }

            @Override
            public String getStringKey(K key) {
                return cacheLoader.getStringKey(key);
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
        }, redisService);
    }
}
