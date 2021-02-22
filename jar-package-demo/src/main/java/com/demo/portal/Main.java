package com.demo.portal;

import com.demo.utils.MyStringUtil;

/**
 * 功能入口类，main方法完成的功能为：拼接命令行参数的所有字符串，然后打印到标准输出
 */
public class Main {
    public static void main(String[] args) {
        // - 判空
        if (args == null || args.length == 0) {
            return;
        }

        // - 拼接参数字符串并打印
        System.out.println(MyStringUtil.join(args));
    }
}
