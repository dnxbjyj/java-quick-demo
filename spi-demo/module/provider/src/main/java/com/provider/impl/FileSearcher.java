package com.provider.impl;

import com.standard.Searcher;

/**
 * 文件搜索器实现
 */
public class FileSearcher implements Searcher {
    @Override
    public String search(String keyword) {
        String res = "使用文件搜索器搜索关键词【" + keyword + "】的返回结果...";
        return res;
    }
}
