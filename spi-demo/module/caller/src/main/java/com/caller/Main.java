package com.caller;

import java.util.ServiceLoader;
import java.util.Iterator;

/**
 * 调用方入口代码
 *
 * 依赖standard jar包
 */
public class Main {
    public static void main(String[] args) {
        // - 获取服务加载器，得到服务迭代器
        ServiceLoader<Searcher> loader = ServiceLoader.load(Search.class);
        Iterator<Searcher> itor = loader.iterator();

        // - 遍历服务，执行服务的业务方法
        while (itor.hasNext()) {
            Searcher searcher = itor.next();
            System.out.println(searcher.search("my keyword"));
        }
    }
} 
