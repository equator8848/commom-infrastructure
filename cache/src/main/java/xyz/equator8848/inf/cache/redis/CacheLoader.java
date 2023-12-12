package xyz.equator8848.inf.cache.redis;

/**
 * @author Equator
 **/
public interface CacheLoader<K, V> {
    V load(K key) throws Exception;

    V reload(K key, V oldValue) throws Exception;

    default boolean isVersionChange(K key, V oldValue) {
        return false;
    }

    String getStringKey(K key);
}
