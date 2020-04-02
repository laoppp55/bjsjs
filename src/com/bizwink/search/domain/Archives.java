package com.bizwink.search.domain;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by kang on 2018/11/19.
 */
public class Archives implements Serializable {

    private int id;
    private String archivesid;   //档案号
    private Timestamp createdate;  //创建时间
    private Timestamp startdate;   //起始时间
    private Timestamp enddate;     //截止时间
    private String archivesname;   //档案 目录 文件题名
    private String archivespath;   //路径
    private String createuser;     //创建用户
    private String num;            //顺序号
    private String identifyyear;   //鉴定年度
    private String productiondate; //文件形成日期

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArchivesid() {
        return archivesid;
    }

    public void setArchivesid(String archivesid) {
        this.archivesid = archivesid;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public Timestamp getStartdate() {
        return startdate;
    }

    public void setStartdate(Timestamp startdate) {
        this.startdate = startdate;
    }

    public Timestamp getEnddate() {
        return enddate;
    }

    public void setEnddate(Timestamp enddate) {
        this.enddate = enddate;
    }

    public String getArchivesname() {
        return archivesname;
    }

    public void setArchivesname(String archivesname) {
        this.archivesname = archivesname;
    }

    public String getArchivespath() {
        return archivespath;
    }

    public void setArchivespath(String archivespath) {
        this.archivespath = archivespath;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getIdentifyyear() {
        return identifyyear;
    }

    public void setIdentifyyear(String identifyyear) {
        this.identifyyear = identifyyear;
    }

    public String getProductiondate() {
        return productiondate;
    }

    public void setProductiondate(String productiondate) {
        this.productiondate = productiondate;
    }
}
