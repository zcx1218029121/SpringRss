package com.rss.zcx.demo.domin;

import java.io.Serializable;
import java.util.List;

/**
 * rss返回值的json
 *
 * @author DuanHu
 * @create 2019-05-15 3:28
 **/
public class Pager implements Serializable {
    private List<Iteam> data;
    private int size;

    public  Pager(){

    }
    public  Pager(int size,List<Iteam> data){
        this.size=size;
        this.data=data;
    }
    public void setData(List<Iteam> data) {

    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Iteam> getData() {
        return data;
    }
}
