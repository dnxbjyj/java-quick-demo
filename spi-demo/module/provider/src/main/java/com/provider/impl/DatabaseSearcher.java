package com.provider.impl;

import com.standard.Searcher;

/**
 * 数据库搜索器实现
 */
public class DatabaseSearcher implements Searcher {
    @Override
    public String search(String keyword) {
        String res = "search keyword: \"" + keyword + "\" by DatabaseSearcher...";
        return res;
    }
}
