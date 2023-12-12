package xyz.equator8848.inf.core.model.exception;

/**
 * 警告级别，不回滚事务
 */
public class TimeoutWarning extends RuntimeException {
    public TimeoutWarning(String msg) {
        super(msg);
    }
}
