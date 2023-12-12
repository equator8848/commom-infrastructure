package xyz.equator8848.inf.core.model.exception;

/**
 * @Author: Equator
 * @Date: 2020/11/1 21:10
 **/

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String msg) {
        super(msg);
    }
}
