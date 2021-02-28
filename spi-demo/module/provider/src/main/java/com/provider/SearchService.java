package com.provider;

import java.util.ServiceLoader;
import java.util.Iterator;

import com.standard.Searcher;

public class SearchService {
    /**
     * 使用指定的搜索器搜索关键词
     *
     * @param searcherClass 搜索器实现类
     * @return 搜索器
     */
    public static Searcher getSearcher(String searcherClass) {
        System.out.println("start SearchService.getSearcher...");
        // - 获取服务加载器，得到服务迭代器
        ServiceLoader<Searcher> loader = ServiceLoader.load(Searcher.class);
        Iterator<Searcher> itor = loader.iterator();
        
        // - 遍历服务，获取指定的搜索器服务
        while (itor.hasNext()) {
            Searcher searcher = itor.next();
            if (searcher.getClass().getCanonicalName().equals(searcherClass)) {
                System.out.println("end SearchService.getSearcher");
                return searcher;
            }
        }
        System.out.println("end SearchService.getSearcher, get searcher is null!");
        return null;
    }
    
    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            return;
        }
        Searcher searcher = getSearcher(args[0]);
        System.out.println(searcher.search(args[1]));
    }
}
