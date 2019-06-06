package com.rss.zcx.demo.domin;

import java.sql.Timestamp;

/**
 * JAVABEAN of Source
 *
 * @author DuanHu
 * @create 2019-05-15 6:54
 **/
public class Source {
    private long sid;
    private String name;
    private String url;


    public long getSid() {
        return sid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
