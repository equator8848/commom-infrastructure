package xyz.equator8848.inf.core.model.exception;

import java.util.Set;

/**
 * @Author: Equator
 * @Date: 2020/11/1 21:16
 **/

public class PreCondition {
    public static void isTrue(boolean exp, String error) {
        if (!exp) {
            throw new VerifyException(error);
        }
    }

    public static void isFalse(boolean exp, String error) {
        if (exp) {
            throw new VerifyException(error);
        }
    }

    public static void isNotNull(Object obj) {
        if (obj == null) {
            throw new VerifyException(("object can not null"));
        }
    }

    public static void isNotNull(Object obj, String msg) {
        if (obj == null) {
            throw new VerifyException(msg);
        }
    }

    public static <T> void isIn(Set<T> set, T data, String msg) {
        if (!set.contains(data)) {
            throw new VerifyException(msg);
        }
    }

    public static <T> void isIn(Set<T> set, T data) {
        if (!set.contains(data)) {
            throw new VerifyException(String.format("%s is not in the set", data.toString()));
        }
    }

    public static void isEquals(Object a, Object b) {
        if (!a.equals(b)) {
            throw new VerifyException(String.format("%s and %s not equals", a, b));
        }
    }

    public static void isEquals(Object a, Object b, String msg) {
        if (!a.equals(b)) {
            throw new VerifyException(msg);
        }
    }
}
