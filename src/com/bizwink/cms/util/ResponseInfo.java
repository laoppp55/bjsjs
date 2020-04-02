package com.bizwink.cms.util;

import java.util.List;

public class ResponseInfo {

    private String code;

    private String msg;


    private List<Article_sgs> date;

    public List<Article_sgs> getDate() {
        return date;
    }

    public void setDate(List<Article_sgs> date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
