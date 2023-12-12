package xyz.equator8848.inf.cache.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: Equator
 * @Date: 2022/1/8 9:57
 **/
@Data
@Setter
public class SimpleCacheElement<D> implements Serializable {
    protected Exception exception;
    private D data;

    public SimpleCacheElement() {
        this.data = null;
        this.exception = null;
    }

    public SimpleCacheElement(D data) {
        this.data = data;
        this.exception = null;
    }

    public SimpleCacheElement(Exception exception) {
        this.data = null;
        this.exception = Objects.requireNonNull(exception);
    }

    public boolean isException() {
        return this.exception != null;
    }

    @JsonIgnore
    public D getDataOrThrow() throws Exception {
        if (exception == null) {
            return data;
        }
        throw exception;
    }
}
