package com.bizwink.search.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by kang on 2019/5/20.
 */
public class SearchWord implements Serializable {

    private int id;
    private String cname; //中文
    private String ename; //拼音
    private String sname; //拼音首字母
    private int num;
    private Timestamp createdate ,publishtime;      //入库日期

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public Timestamp getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Timestamp publishtime) {
        this.publishtime = publishtime;
    }
}
