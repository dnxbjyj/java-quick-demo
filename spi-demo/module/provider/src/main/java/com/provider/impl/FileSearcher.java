package com.provider.impl;

import com.standard.Searcher;

/**
 * 文件搜索器实现
 */
public class FileSearcher implements Searcher {
    @Override
    public String search(String keyword) {
        String res = "search keyword: \"" + keyword + "\" by FileSearcher...";
        return res;
    }
}
