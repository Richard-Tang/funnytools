package com.funnysec.richardtang.funnytools.constant;

import java.util.regex.Pattern;

/**
 * 正则常量
 *
 * @author RichardTang
 * @date 2020/3/15
 */
public class Regular {

    public static final Pattern LINE_PATTERN = Pattern.compile("_(\\w)");

    public static final Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");

    public static final Pattern DOMAIN_PATTERN = Pattern.compile("^((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|\\d)(\\.((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|\\d)){3}$|^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$");

    public static final Pattern PHONE_PATTERN = Pattern.compile("^[1][3-9][0-9]{9}$");

    public static final Pattern IP_PATTERN = Pattern.compile("/((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)/");
}
