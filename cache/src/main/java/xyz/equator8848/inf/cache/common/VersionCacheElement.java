package xyz.equator8848.inf.cache.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: Equator
 * @Date: 2022/1/8 9:57
 **/
@Data
@Setter
public class VersionCacheElement<V, D> extends SimpleCacheElement<D> implements Serializable {
    private V version;

    public VersionCacheElement(V version, D data) {
        super(data);
        this.version = version;
    }

    public VersionCacheElement(Exception e) {
        super(e);
        this.version = null;
    }

    @JsonIgnore
    public V getVersionOrThrow() throws Exception {
        if (exception == null) {
            return version;
        }
        throw exception;
    }
}
