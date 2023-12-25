package xyz.equator8848.inf.security.random;


import java.util.UUID;

public class IDUtil {
    private static final String randomCharset = "ABCDEFGHJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int randomCharsetLen = randomCharset.length();

    /**
     * 使用UUID生成绝对唯一的32位字符串随机序列
     *
     * @return String
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成随机字符串
     *
     * @param bit
     * @return
     */
    public static String getRandomChar(Integer bit) {
        StringBuffer authCode = new StringBuffer();
        for (int i = 0; i < bit; i++) {
            authCode.append(randomCharset.charAt((int) (Math.random() * randomCharsetLen)));
        }
        return authCode.toString();
    }

    /**
     * 获取指定位数的数字验证码
     *
     * @param count
     * @return
     */
    public static String generatedRandomCode(int count) {
        return String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, count - 1))).substring(0, count);
    }
}
