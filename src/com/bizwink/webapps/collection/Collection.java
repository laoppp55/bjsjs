package com.bizwink.webapps.collection;

public class Collection {
    private int id;
    private int siteid;
    private int articleid;
    private String ename;
    private String cname;
    private int type;                       //1:整数，2：浮点，3：字符串，4：长文本，5：时间戳
    private String textvalue;
    private String stringvalue;
    private double numericvalue;
    private float floatvalue;

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

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTextvalue() {
        return textvalue;
    }

    public void setTextvalue(String textvalue) {
        this.textvalue = textvalue;
    }

    public String getStringvalue() {
        return stringvalue;
    }

    public void setStringvalue(String stringvalue) {
        this.stringvalue = stringvalue;
    }

    public double getNumericvalue() {
        return numericvalue;
    }

    public void setNumericvalue(double numericvalue) {
        this.numericvalue = numericvalue;
    }

    public float getFloatvalue() {
        return floatvalue;
    }

    public void setFloatvalue(float floatvalue) {
        this.floatvalue = floatvalue;
    }
}
