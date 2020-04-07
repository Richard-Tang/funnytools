package com.funnysec.richardtang.funnytools.utils;

import com.funnysec.richardtang.funnytools.constant.Character;
import com.funnysec.richardtang.funnytools.constant.Regular;

import java.util.regex.Matcher;

/**
 * 字段工具类
 *
 * @author RichardTang
 * @date 2020年3月15日22:02:13
 */
public class FieldUtils {

    /**
     * 驼峰转下划线
     *
     * @param str 被转换字符串
     * @return String 下划线格式的字符串
     */
    public static String humpToLine(String str) {
        return String.join(Character.UNDERLINE, str.replaceAll(Regular.HUMP_PATTERN.toString(), ",$0")
                .split(Character.COMMA))
                .toLowerCase();
    }

    /**
     * 下划线转驼峰
     *
     * @param str 被转换字符串
     * @return String 驼峰格式的字符串
     */
    public static String lineToHump(String str) {
        Matcher matcher = Regular.LINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
