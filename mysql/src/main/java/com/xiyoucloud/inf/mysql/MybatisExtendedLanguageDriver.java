package com.xiyoucloud.inf.mysql;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 弥补注解的不足
 *
 * @Author: Equator
 * @Date: 2022/1/15 10:20
 **/

@Slf4j
public class MybatisExtendedLanguageDriver extends XMLLanguageDriver implements LanguageDriver {

    private final Pattern inPattern = Pattern.compile("#in\\s*\\{([^}]+)}");

    private final Pattern valuesPattern = Pattern.compile("#values\\s*\\{([^}]+)}\\s*\\{([^}]+)}");

    public MybatisExtendedLanguageDriver() {
    }

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        String convertedSql = this.convert(script);
        log.debug(convertedSql);
        return super.createSqlSource(configuration, convertedSql, parameterType);
    }

    /**
     * 启动时替换一次
     *
     * @param script
     * @return
     */
    protected String convert(String script) {
        Matcher matcher = this.inPattern.matcher(script);
        if (matcher.find()) {
            script = matcher.replaceAll("in (<foreach collection=\"$1\" item=\"__item\" separator=\",\"> #{__item} </foreach>)");
            script = "<script>" + script + "</script>";
        } else {
            matcher = this.valuesPattern.matcher(script);
            if (matcher.find()) {
                String listName = matcher.group(1);
                List<String> params = List.of(matcher.group(2).split(","));
                StringBuilder sb = new StringBuilder();
                sb.append("values ");
                sb.append("<foreach collection=\"");
                sb.append(listName);
                sb.append("\" item=\"__item\" separator=\",\" >");
                sb.append("(");
                List<String> variables = new ArrayList<>();
                for (String param : params) {
                    if (!param.contains("(")) {
                        variables.add("#{__item." + param + "}");
                    } else {
                        variables.add(param);
                    }
                }
                sb.append(String.join(", ", variables));
                sb.append(") </foreach>");
                script = matcher.replaceAll(sb.toString());
                script = "<script>" + script + "</script>";
            }
        }
        return script;
    }

    public static void main(String[] args) {
        String inString = "SELECT * FROM t_tag WHERE id #in {ids}";
        System.out.println(new MybatisExtendedLanguageDriver().convert(inString));

        String valueString = "INSERT INTO t_tag (id,name) #values {tagList} {id, tagName}";
        System.out.println(new MybatisExtendedLanguageDriver().convert(valueString));
    }
}
