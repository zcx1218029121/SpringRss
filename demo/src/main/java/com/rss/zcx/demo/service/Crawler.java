package com.rss.zcx.demo.service;

import com.rss.zcx.demo.dao.ItemDao;
import com.rss.zcx.demo.domin.Iteam;
import com.rss.zcx.demo.domin.Source;
import com.rss.zcx.demo.net.OkHttpUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * 爬虫类提供数据；
 * <h1>多线程 ，增量爬取</h1>
 * 每个item link是唯一的 redis  的时间复杂度是的是O(1)用Redis对item进行去重从而实现增量爬取；
 * 爬虫每次运行结束后修改一个key判断是否有修改 key值为
 * @author DuanHu
 **/
@Component

public class Crawler {
    private static String CRAWLER_KEY = "crawler_key";
    private int count;
    private int repeatCount;
    private  int erroCrawler;
    private RedisTemplate redisTemplate;

    private ItemDao itemDao;

    private int thread = Runtime.getRuntime().availableProcessors();

    private List<Source> sources;

    private List<Iteam> list = Collections.synchronizedList(new ArrayList<>());

    private ExecutorService fixedThreadPool ;


    private void getResponse(Source source) {
        fixedThreadPool.execute(() -> parsXml(OkHttpUtil.get(source.getUrl(), null), source));
    }


    private void parsXml(String r, Source source) {
        try {
            Document document = DocumentHelper.parseText(r);
            List<Element> element = document.getRootElement().element("channel").elements("item");
               count+=element.size();
            for (Element temp : element) {
                if (redisTemplate.opsForHash().hasKey(CRAWLER_KEY, temp.elementText("link"))) {
                    //如果 redis 有这条数据 就不爬了放数据库了
                    repeatCount++;
               } else {
                    Iteam iteam = new Iteam();
                    iteam.setTitle(temp.elementText("title"));
                    iteam.setDes(temp.elementText("description"));
                    iteam.setGuid(temp.elementText("guid"));
                    iteam.setLink(temp.elementText("link"));
                    iteam.setSource(source.getName());
                    iteam.setSourceId(source.getSid());
                    list.add(iteam);
                    //如果有数据的话就添加到redis
                    redisTemplate.opsForHash().put(CRAWLER_KEY, iteam.getLink(), iteam.getTitle());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            erroCrawler++;
        }

    }

    /**
     * 启动线程池的方法 使用结束的时候必须停止线程池
     */
    private void exeCalls() {
        //redisTemplate.delete(CRAWLER_KEY);
        initRedit();
        fixedThreadPool = new ThreadPoolExecutor(thread, thread, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
        if (sources != null) {
            for (Source source : sources) {
                getResponse(source);
            }
        }
    }

    public List<Iteam> getList() {
        return list;
    }


    /**
     * 停止线程池的并堵塞等待回调的方法
     */
    public void stopAllCalls() {
        fixedThreadPool.shutdown();
        try {
            fixedThreadPool.awaitTermination(50, TimeUnit.SECONDS);

            System.out.println("启动爬虫数"+sources.size()+"解析失败"+  erroCrawler);
            System.out.println(System.currentTimeMillis()+"爬虫执行结束"+"共爬取"+count+"去重"+repeatCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void exeCallsWithClear() {
        list.clear();
        exeCalls();
    }


    public List<Source> getSources() {
        return sources;
    }

    public Crawler setSources(List<Source> sources) {
        this.sources = sources;
        return this;
    }

    private Date getLastBulidTime(String data) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH);
        try {
            date = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    private void initRedit() {
        itemDao.getAllItem().forEach(
                item -> redisTemplate.opsForHash().put(CRAWLER_KEY, item.getLink(), item.getDes())
        );

    }

    @Autowired
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}
