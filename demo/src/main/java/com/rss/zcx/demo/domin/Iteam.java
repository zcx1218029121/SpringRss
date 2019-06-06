package com.rss.zcx.demo.domin;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * iteam
 *
 * @author DuanHu
 * @create 2019-05-14 7:08
 **/
public class Iteam implements Serializable {
    /**
     * 来自的源
     */
    private long u_id;
    private long sourceId;
    private String source;
    private String title;
    private String des;
    private String link;
    private String guid;
    @DateTimeFormat(pattern = "MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String strDate;
   public Iteam(String title,String des,String link,String guid){
        this.title=title;
        this.des=des;
        this.link=link;
        this.guid=guid;
    }
   public Iteam(){
    }
    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public java.util.Date getDate() {
        return date;
    }

    public long getU_id() {
        return u_id;
    }

    public void setU_id(long u_id) {
        this.u_id = u_id;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public String getStrDate() {
        return strDate;
    }
}
