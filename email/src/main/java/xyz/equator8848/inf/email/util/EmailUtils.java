package xyz.equator8848.inf.email.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {
    /**
     * 检查邮箱正确性
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        String regex = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
