package com.rss.zcx.demo.Scheduled;

import com.rss.zcx.demo.dao.ItemDao;
import com.rss.zcx.demo.dao.SourceDao;
import com.rss.zcx.demo.service.Crawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * spring 定定时爬虫任务
 * 1. 增量
 * 2. 多线程
 * 3. redis真的好用
 *
 * @author DuanHu
 * @create 2019-05-22 12:44
 **/
@Component
@Configuration

@EnableScheduling
public class CrawlerScheduled {
    /**
     * 数据源的Dao层次
     */
    private SourceDao sourceDao;
    /**
     * 爬虫类
     */
    private Crawler crawler;
    /**
     * item的dao层
     */
    private ItemDao itemDao;

    /**
     * 爬虫0.8小时执行一次
     */
    @Scheduled(fixedRate =3000000)
    private void configureTasks() {
        crawler.setSources(sourceDao.getAllScores());
        crawler.exeCallsWithClear();
        crawler.stopAllCalls();
        //数据落库
        crawler.getList().forEach(iteam -> {
            itemDao.addItem(iteam);
        });

    }

    @Autowired
    public void setSourceDao(SourceDao sourceDao) {
        this.sourceDao = sourceDao;
    }
    @Autowired
    public void setCrawler(Crawler crawler) {
        this.crawler = crawler;
    }
    @Autowired
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
}
