package com.bizwink.webapps.collection;

public class SimpleCollection {
    private int id;
    private int siteid;
    private int articleid;
    private String title;
    private String content;
    private int pubflag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public int getArticleid() {
        return articleid;
    }

    public void setArticleid(int articleid) {
        this.articleid = articleid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPubflag() {
        return pubflag;
    }

    public void setPubflag(int pubflag) {
        this.pubflag = pubflag;
    }
}
