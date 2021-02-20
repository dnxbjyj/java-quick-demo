package com.demo.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 */
public class MyStringUtil {
    /**
     * 拼接字符串数组的所有字符串
     */
    public static String join(String[] strs) {
        return StringUtils.join(strs);
    }
}
