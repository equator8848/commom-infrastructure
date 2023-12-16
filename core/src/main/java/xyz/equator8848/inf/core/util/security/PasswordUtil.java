package xyz.equator8848.inf.core.util.security;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.Crypt;
import xyz.equator8848.inf.core.model.exception.PreCondition;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * decrypt 解密
 * encrypt 加密
 *
 * @Author: Equator
 * @Date: 2022/9/28 23:37
 **/

public class PasswordUtil {
    public static String generateSha512CryptPassword(String password, String salt) {
        return Crypt.crypt(password, String.format("$6$%s", salt));
    }

    public static String generateSha1CryptPassword(String password, String salt) {
        // 获取指定摘要算法的messageDigest对象，此处的sha代表sha1
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        // 调用digest方法，进行加密操作
        messageDigest.update(password.getBytes(StandardCharsets.UTF_8));
        messageDigest.update(salt.getBytes(StandardCharsets.US_ASCII));
        return Hex.encodeHexString(messageDigest.digest());
    }

    /**
     * 初始化jupyter密码
     *
     * @param password
     * @return
     */
    public static String generateSha1CryptPasswordForJupyter(String password) {
        String salt = IDUtil.getRandomChar(16);
        String cryptPassword = generateSha1CryptPassword(password, salt);
        return String.format("sha1:%s:%s", salt, cryptPassword);
    }


    public static String parseSaltFromEncryptedPassword(String encryptedPassword) {
        String[] splitSequences = encryptedPassword.split("\\$");
        PreCondition.isTrue(splitSequences.length > 2, "密码格式不合法，合法格式为$6$asdfghjkl$Hfekwg7x2tFhGK7LnGrvzHUh/1NjQeF/hMEXmXoTyGyskoz.NYc0Rr7YCm.gZ.57pckTeKlsNGXp2Zm7ULEzS0");
        return splitSequences[2];
    }

    public static String generateSha512CryptPassword(String password) {
        String salt = IDUtil.getRandomChar(12);
        return generateSha512CryptPassword(password, salt);
    }
}
