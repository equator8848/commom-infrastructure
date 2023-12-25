package xyz.equator8848.inf.security.des;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import xyz.equator8848.inf.core.model.exception.InnerException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author: Equator
 * @Date: 2022/9/23 17:10
 **/
@Slf4j
public class DESUtil {
    /**
     * 加密
     *
     * @param originKey
     * @param plainText
     * @return
     */
    public static String encrypt(String originKey, String plainText) {
        SecretKeySpec key = new SecretKeySpec(originKey.getBytes(), "DES");
        try {
            // 1.获取加密算法工具类
            Cipher cipher = Cipher.getInstance("DES");
            // 2.对工具类对象进行初始化,
            // mode:加密/解密模式
            // key:对原始秘钥处理之后的秘钥
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 3.用加密工具类对象对明文进行加密
            byte[] encipherByte = cipher.doFinal(plainText.getBytes());
            // 防止乱码，使用Base64编码
            return Base64.encodeBase64String(encipherByte);
        } catch (Exception e) {
            log.error("DES encrypt failed", e);
            throw new InnerException("DES encrypt failed");
        }
    }

    /**
     * 解密
     *
     * @param originKey
     * @param cipherText
     * @return
     */
    public static String decrypt(String originKey, String cipherText) {
        SecretKeySpec key = new SecretKeySpec(originKey.getBytes(), "DES");
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decode = Base64.decodeBase64(cipherText);
            byte[] decipherByte = cipher.doFinal(decode);
            return new String(decipherByte);
        } catch (Exception e) {
            log.error("DES decrypt failed", e);
            throw new InnerException("DES decrypt failed");
        }
    }
}
