package com.bizwink.search.domain;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-6-6
 * Time: 下午5:30
 * To change this template use File | Settings | File Templates.
 */


public class Article implements Serializable {
    private int id, sortid;                    //文章ID
    private int siteid;               //站点ID
    private String maintitle;          //主标题
    private String vicetitle;          //副标题
    private String summary;            //摘要
    private String keyword;            //关键词
    private String source;             //来源
    private String content;            //内容
    private String author;             //作者
    private Timestamp createdate ,publishtime;      //入库日期
    private String dirname;            //发布后的目录名
   // private float saleprice;            //商品销售价格
  //  private float inPrice;              //商品进价
   // private float marketprice;         //商品市场价格
  //  private float vipprice;            //商品VIP价格
    private String pic;            //商品小图片
    private String bigpic;        //商品大图片
    private String articlepic;          //文章图片
    private String brand;                 //商品品牌
   // private int stocknum;                //商品库存
    private String defineurl;
    private int fromsiteid;
    private int columnid;
    private String sitename;
    private int   status;
    private String filename;
    private String columnParents;
    private String articleclass;      //从根目录到文章所在目录的id的联合号
    private String cname;        //栏目名称
    private int urltype;        //0 本地链接 1外部链接


    public String getArticleclass() {
        return articleclass;
    }

    public void setArticleclass(String articleclass) {
        this.articleclass = articleclass;
    }

    public String getColumnParents() {
        return columnParents;
    }

    public void setColumnParents(String columnParents) {
        this.columnParents = columnParents;
    }

    public int getSortid() {
        return sortid;
    }

    public void setSortid(int sortid) {
        this.sortid = sortid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Timestamp getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Timestamp publishtime) {
        this.publishtime = publishtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getColumnid() {
        return columnid;
    }

    public void setColumnid(int columnid) {
        this.columnid = columnid;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

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

    public String getMaintitle() {
        return maintitle;
    }

    public void setMaintitle(String maintitle) {
        this.maintitle = maintitle;
    }

    public String getVicetitle() {
        return vicetitle;
    }

    public void setVicetitle(String vicetitle) {
        this.vicetitle = vicetitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Timestamp getCreateDate() {
        return createdate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createdate = createDate;
    }

    public String getDirName() {
        return dirname;
    }

    public void setDirName(String dirName) {
        this.dirname = dirName;
    }

    /*public float getSalePrice() {
        return saleprice;
    }

    public void setSalePrice(float salePrice) {
        this.saleprice = salePrice;
    }

    public float getInPrice() {
        return inPrice;
    }

    public void setInPrice(float inPrice) {
        this.inPrice = inPrice;
    }

    public float getMarketPrice() {
        return marketprice;
    }

    public void setMarketPrice(float marketPrice) {
        this.marketprice = marketPrice;
    }

    public float getVipprice() {
        return vipprice;
    }

    public void setVipprice(float vipprice) {
        this.vipprice = vipprice;
    }*/

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getBigpic() {
        return bigpic;
    }

    public void setBigpic(String bigpic) {
        this.bigpic = bigpic;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
/*
    public int getStockNum() {
        return stocknum;
    }

    public void setStockNum(int stockNum) {
        this.stocknum = stockNum;
    }*/

    public String getArticlepic() {
        return articlepic;
    }

    public void setArticlepic(String articlepic) {
        this.articlepic = articlepic;
    }

    public String getDefineurl() {
        return defineurl;
    }

    public void setDefineurl(String defineurl) {
        this.defineurl = defineurl;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public String getDirname() {
        return dirname;
    }

    public void setDirname(String dirname) {
        this.dirname = dirname;
    }

    /*public float getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(float saleprice) {
        this.saleprice = saleprice;
    }

    public float getMarketprice() {
        return marketprice;
    }

    public void setMarketprice(float marketprice) {
        this.marketprice = marketprice;
    }

    public int getStocknum() {
        return stocknum;
    }

    public void setStocknum(int stocknum) {
        this.stocknum = stocknum;
    }
*/
    public int getFromsiteid() {
        return fromsiteid;
    }

    public void setFromsiteid(int fromsiteid) {
        this.fromsiteid = fromsiteid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getUrltype() {
        return urltype;
    }

    public void setUrltype(int urltype) {
        this.urltype = urltype;
    }


}
