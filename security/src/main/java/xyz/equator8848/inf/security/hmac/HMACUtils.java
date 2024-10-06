package xyz.equator8848.inf.security.hmac;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

/**
 * 签名-加签验签工具
 *
 * @author Equator
 * @date 2024/6/25 9:08
 */
@Slf4j
public class HMACUtils {
    private static final String ALGORITHM = "HmacSHA256";

    public static String keyGen() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey key = keyGen.generateKey();
            return Base64.encodeBase64String(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean valid(String message, String secret, String signature) {
        return signature != null && signature.equals(sign(message, secret));
    }

    public static String sign(String message, String secret) {
        try {
            Mac hmac = Mac.getInstance(ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            hmac.init(secretKey);
            byte[] bytes = hmac.doFinal(message.getBytes());
            return Base64.encodeBase64String(bytes);
        } catch (Exception ex) {
            log.error("签名错误：", ex);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(HMACUtils.keyGen());
    }
}
