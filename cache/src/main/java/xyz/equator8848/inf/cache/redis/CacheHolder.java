package xyz.equator8848.inf.cache.redis;

/**
 * @author Equator
 **/
public interface CacheHolder<K, V> {
    /**
     * 获取数据
     *
     * @param key
     * @param expireSecond
     * @return
     */
    V get(K key, long expireSecond);

    /**
     * 比较版本是否变化，变化的话，获取新数据
     *
     * @param key
     * @param expireSecond
     * @return
     */
    V compareAndGet(K key, long expireSecond);

    /**
     * 刷新有效期
     *
     * @param key
     * @param expireSecond
     */
    void refresh(K key, long expireSecond);

    /**
     * 移除缓存
     *
     * @param key
     */
    void remove(K key);
}
