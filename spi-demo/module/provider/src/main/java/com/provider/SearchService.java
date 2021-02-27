package com.provider;

import java.util.ServiceLoader;
import java.util.Iterator;

import com.standard.Searcher;

public class SearchService {
    public static void search() {
        System.out.println("start SearchService.search...");
        // - 获取服务加载器，得到服务迭代器
        ServiceLoader<Searcher> loader = ServiceLoader.load(Searcher.class);
        Iterator<Searcher> itor = loader.iterator();
        // - 遍历服务，执行服务的业务方法
        while (itor.hasNext()) {
            Searcher searcher = itor.next();
            System.out.println(searcher.search("my keyword"));
        }
        System.out.println("end SearchService.search");
    }
    
    public static void main(String[] args) {
        search();
    }
}
