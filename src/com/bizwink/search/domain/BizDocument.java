package com.bizwink.search.domain;

import com.bizwink.search.util.CommUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

/**
 * Created by IntelliJ IDEA.
 * User: jackzhang
 * Date: 13-10-14
 * Time: 下午8:13
 * To change this template use File | Settings | File Templates.
 */
public class BizDocument {
    //主要用于去除不必要的索引建立，比如空字符串等直接返回null确保不索引
    public static String trimField(String tempStr){
        if (tempStr ==null || tempStr.equals("")||tempStr.length()==1){
            return null;
        }
        return  tempStr;
    }
    public static Document Document(int siteid,Article article){
        int articleid=0;
        int sortid = 0;
        String maintitle = null;
        String keyword = null;
        String summary = null;
        String content = null;
        String articlepic = "";
       /* float saleprice = 0.00f;            	            //电子商务使用  商品售价
        float inprice = 0.00f;                            	//电子商务使用  商品进价
        float marketprice = 0.00f;          	            //电子商务使用  商品市场价
        float vipprice = 0.00f;          	                //电子商务使用  商品VIP价
        int   stocknum = 0;        */     	                //电子商务使用  商品库存量
        String brand = "";                	            //电子商务使用  商品品牌
        String pic = "";                  	            //电子商务使用  商品小图片
        String bigpic = "";               	            //电子商务使用  商品大图片
        String lastupdate = null;
        String publishtime = null;
        String filename = null;
        String createdate =null;

        articleid = article.getId();

        /********************/
        content = trimField(article.getContent().trim());
        maintitle = trimField(article.getMaintitle());
        summary = trimField(article.getSummary());
        keyword = trimField(article.getKeyword());
        /*********如果文章没有录入summary,切content前100个字符做summary*************/
        if (summary ==null || summary.length() ==0){
            if (content !=null){
                content  = CommUtil.html2Text(content.trim());
                int len = content.length();
                if (len >100) {
                    len =100;
                }
                summary = content.substring(0,len);
            }
        }

        /************************/
        articlepic = article.getArticlepic();
        if (articlepic == null) articlepic = "";
       /* saleprice = article.getSalePrice();
        inprice = article.getInPrice();
        marketprice = article.getMarketPrice();
       // vipprice = article.getVIPPrice();
        stocknum = article.getStockNum();*/
        brand = article.getBrand();
        if (brand == null) brand = "";
       // pic = article.getProductPic();
        if (pic == null) pic = "";
       // bigpic = article.getProductBigPic();
        if (bigpic == null) bigpic = "";
        
        String dirname = article.getDirName();
        createdate = CommUtil.timestamp2string(article.getCreatedate(),"yyyy-MM-dd");
        publishtime = CommUtil.timestamp2string(article.getPublishtime(),"yyyy-MM-dd");
        filename = article.getFilename();

        Document doc = new Document();
/*
        try {
            doc.add(new StringField ("id", String.valueOf(articleid), Field.Store.YES));
            doc.add(new StringField ("siteid", String.valueOf(siteid), Field.Store.YES));
            doc.add(new StringField ("sortid", String.valueOf(sortid), Field.Store.YES));
            */
/*doc.add(new StringField ("saleprice", String.valueOf(saleprice), Field.Store.YES));
            doc.add(new StringField ("inprice", String.valueOf(inprice), Field.Store.YES));
            doc.add(new StringField ("marketprice", String.valueOf(marketprice), Field.Store.YES));
            doc.add(new StringField ("vipprice", String.valueOf(vipprice), Field.Store.YES));
            doc.add(new StringField ("stocknum", String.valueOf(stocknum), Field.Store.YES));*//*

            doc.add(new StringField ("brand", brand, Field.Store.YES,Field.Index.NOT_ANALYZED));
            doc.add(new StringField ("pic", pic, Field.Store.YES));
            doc.add(new StringField ("bigpic", bigpic, Field.Store.YES));
            doc.add(new StringField ("columnid", String.valueOf(article.getColumnid()), Field.Store.YES));
            doc.add(new StringField ("articlepic", articlepic, Field.Store.YES));
            doc.add(new StringField ("createdate",  createdate , Field.Store.YES));
            doc.add(new StringField("publishtime",  publishtime , Field.Store.YES));
            doc.add(new StringField("dirname", dirname, Field.Store.YES));
            if (filename != null)  {
                doc.add(new StringField("filename", filename, Field.Store.YES));
            }
            if(maintitle != null){
                doc.add(new TextField("maintitle", maintitle, Field.Store.YES));
            }
            if(summary != null){
                doc.add(new TextField("summary", summary, Field.Store.YES));
            }
            if(keyword != null){
                doc.add(new TextField("keyword", keyword, Field.Store.YES));
            }
            if(content != null){
                doc.add(new TextField("content", content, Field.Store.NO));
            }
            //将该文章的栏目id及该栏目的所有父id存起来---确保该字段也被索引
            doc.add(new TextField ("columnParents", article.getColumnParents(), Field.Store.YES));

        }catch(Exception e){
            e.printStackTrace();
        }
*/
/*
        if (sitename != null) {
            try {
                doc.add(new StringField("sitename", sitename, Field.Store.YES));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }*/

        /*if(summary != null){
            //System.out.println("have summary=" + summary);
            try{

            }catch(Exception e){
                e.printStackTrace();
            }
        } else {
            *//*summary = StringUtil.getContentFromHTML(content);
            summary = summary.trim();
            //content = StringUtil.getContentFromHTML(content,"<style ","</style>");
            //summary = StringUtil.getContentFromHTML(content,"<",">");
            //System.out.println(summary.length());
            if (summary.length() > 50) summary = summary.substring(0,50);
            try{
                doc.add(new Field("summary", summary, Field.Store.YES, Field.Index.TOKENIZED));
            }catch(Exception e){
                e.printStackTrace();
            }  *//*
        }*/



        return doc;
    }

   /* public static Document Document(Article article){

        Document doc = new Document();
        try {
            doc.add(new StringField("id", String.valueOf(article.getId()), Field.Store.YES));
            doc.add(new StringField("siteid", String.valueOf(article.getSiteid()), Field.Store.YES));
            doc.add(new StringField("columnid", String.valueOf(article.getColumnid()), Field.Store.YES));
            if (article.getPublishtime() !=null) {
                doc.add(new StringField("publishtime", CommUtil.timestamp2string(article.getPublishtime(),"yyyy-MM-dd"), Field.Store.YES));
            }
            doc.add(new StringField("dirname", article.getDirName(), Field.Store.YES));
            if (article.getSitename() != null) {
                doc.add(new StringField("sitename", article.getSitename(), Field.Store.YES));
            }
            if(article.getMaintitle() != null){
                TextField tmp = new TextField("maintitle", article.getMaintitle(),Field.Store.YES);
                //标题权重加大
                tmp.setBoost(10);
                doc.add(tmp);
            }
            if(article.getSummary() != null){
                doc.add(new TextField("summary", article.getSummary(), Field.Store.YES));
            }

            if(article.getKeyword() != null){
                    doc.add(new TextField("keyword", article.getKeyword(), Field.Store.YES));
            }

            if(article.getContent() != null){
                    doc.add(new TextField("content", article.getContent(), Field.Store.NO));
            }
            if (article.getFilename() != null)
            doc.add(new StringField("filename", article.getFilename(), Field.Store.YES));
        } catch(Exception e) { }
        return doc;
    }*/


    private BizDocument() {

    }
}
