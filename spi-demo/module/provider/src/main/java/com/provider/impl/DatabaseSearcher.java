package com.provider.impl;

import com.standard.Searcher;

/**
 * 数据库搜索器实现
 */
public class DatabaseSearcher implements Searcher {
    @Override
    public String search(String keyword) {
        String res = "使用数据库搜索器搜索关键词【" + keyword + "】的返回结果...";
        return res;
    }
}
