package xyz.equator8848.inf.core.util.security;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Equator
 * @Date: 2022/9/18 12:02
 **/

public class MD5Util {
    private final static MessageDigest md5Instance;

    static {
        try {
            md5Instance = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String md5(String salt, String source) {
        return DigestUtils.md5Hex(String.format("%s#%s", salt, source).getBytes());
    }

    public static String md5(String source) {
        return DigestUtils.md5Hex(source.getBytes());
    }
}
