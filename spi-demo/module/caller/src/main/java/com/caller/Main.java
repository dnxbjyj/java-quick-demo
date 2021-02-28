package com.caller;

import com.standard.Searcher;
import com.provider.SearchService;

/**
 * 调用方入口代码
 * <p>
 * 依赖provider.jar包
 */
public class Main {
    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            return;
        }
        Searcher searcher = SearchService.getSearcher(args[0]);
        System.out.println(searcher.search(args[1]));
    }
} 
