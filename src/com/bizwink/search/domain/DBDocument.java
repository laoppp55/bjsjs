package com.bizwink.search.domain;

import com.bizwink.search.util.CommUtil;
import org.apache.lucene.document.*;

/**
 * Created by petersong on 16-7-17.
 */
public class DBDocument {
    //文档
    public static Document Document(int siteid,Article article){
        int articleid=0;
        int sortid = 0;
        String maintitle = null;
        String keyword = null;
        String summary = null;
        String content = null;
        String articlepic = "";
        float saleprice = 0.00f;            	            //电子商务使用  商品售价
        float inprice = 0.00f;                            	//电子商务使用  商品进价
        float marketprice = 0.00f;          	            //电子商务使用  商品市场价
        float vipprice = 0.00f;          	                //电子商务使用  商品VIP价
        int   stocknum = 0;             	                //电子商务使用  商品库存量
        String brand = "";                	            //电子商务使用  商品品牌
        String pic = "";                  	            //电子商务使用  商品小图片
        String bigpic = "";               	            //电子商务使用  商品大图片
        String publishtime = null;
        String filename = null;
        String cname = null;
        String defineurl=null;
        int urltype =0;

        articleid = article.getId();
        content = article.getContent();
        maintitle = article.getMaintitle();
        summary = article.getSummary();
        keyword = article.getKeyword();
        articlepic = article.getArticlepic();
        if (articlepic == null) articlepic = "";
        brand = article.getBrand();
        if (brand == null) brand = "";
        if (bigpic == null) bigpic = "";
        String dirname = article.getDirName();
        filename = article.getFilename();
        cname = article.getCname();
        siteid = article.getSiteid();
        defineurl = article.getDefineurl();
        urltype = article.getUrltype();

        Document doc = new Document();
        try {
            doc.add(new IntField("id", articleid, IntField.Store.YES));
            doc.add(new IntField("siteid", siteid, IntField.Store.YES));
            doc.add(new StringField("classid", article.getColumnParents(), StringField.Store.YES));
            doc.add(new IntField("sortid", sortid, IntField.Store.YES));
            doc.add(new FloatField("saleprice", saleprice, FloatField.Store.YES));
            doc.add(new FloatField("inprice", inprice, FloatField.Store.YES));
            doc.add(new FloatField("marketprice", marketprice, FloatField.Store.YES));
            doc.add(new FloatField("vipprice", vipprice, FloatField.Store.YES));
            doc.add(new IntField("stocknum", stocknum, IntField.Store.YES));
            doc.add(new StringField("brand", brand, StringField.Store.YES));
            doc.add(new StringField("pic", pic, StringField.Store.YES));
            doc.add(new StringField("bigpic", bigpic, StringField.Store.YES));
            doc.add(new IntField("columnid", article.getColumnid(), Field.Store.YES));
            doc.add(new StringField("articlepic", articlepic, Field.Store.YES));
            IntField createDateField = new IntField("createdate", Integer.parseInt(DateTools.dateToString(article.getCreatedate(), DateTools.Resolution.DAY)),Field.Store.YES);
            doc.add(createDateField);
            IntField pubDateField = new IntField("publishtime", Integer.parseInt(DateTools.dateToString(article.getPublishtime(), DateTools.Resolution.DAY)),Field.Store.YES);
            doc.add(pubDateField);
            System.out.println("createdate==="  + DateTools.dateToString(article.getCreatedate(),DateTools.Resolution.DAY));
            doc.add(new StringField("cname", cname, StringField.Store.YES));
            doc.add(new IntField("urltype", urltype, IntField.Store.YES));
        }catch(Exception e){
            e.printStackTrace();
        }

        if(maintitle != null){
            try{
                doc.add(new TextField("maintitle", maintitle, TextField.Store.YES));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if(summary != null){
            //System.out.println("have summary=" + summary);
            try{
                doc.add(new TextField("summary", summary, TextField.Store.YES));
            }catch(Exception e){
                e.printStackTrace();
            }
        } else {
            summary = CommUtil.getContentFromHTML(content);
            summary = summary.trim();
            //content = StringUtil.getContentFromHTML(content,"<style ","</style>");
            //summary = StringUtil.getContentFromHTML(content,"<",">");
            //System.out.println(summary.length());
            if (summary.length() > 50) summary = summary.substring(0,50);
            try{
                doc.add(new TextField("summary", summary, TextField.Store.YES));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if(keyword != null){
            try{
                doc.add(new TextField("keyword", keyword, TextField.Store.YES));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if(content != null){
            try{
                doc.add(new TextField("content", content, TextField.Store.NO));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        if(defineurl != null ){
            try{
                doc.add(new StringField("defineurl", defineurl, StringField.Store.YES));
            }catch(Exception e){
                e.printStackTrace();
            }

        }


        try{
            if (filename != null)
                doc.add(new StringField("filename", filename, StringField.Store.YES));
            doc.add(new StringField("dirname", dirname, StringField.Store.YES));
        }catch(Exception e){
            e.printStackTrace();
        }

        return doc;
    }

    public static Document Document(Article article){
        Document doc = new Document();

        doc.add(new IntField("id", article.getId(), Field.Store.YES));
        doc.add(new IntField("siteid", article.getSiteid(), Field.Store.YES));
        doc.add(new IntField("sortid", article.getSortid(), Field.Store.YES));
        doc.add(new IntField("columnid", article.getColumnid(), Field.Store.YES));

        if (article.getSitename() != null) {
            try {
                doc.add(new StringField("sitename", article.getSitename(), StringField.Store.YES));
            } catch(Exception e) { }
        }

        if(article.getMaintitle() != null){
            try{
                doc.add(new TextField("maintitle", article.getMaintitle(), Field.Store.YES));
            }catch(Exception e){
            }
        }

        if(article.getSummary() != null){
            try{
                doc.add(new TextField("summary", article.getSummary(), TextField.Store.YES));
            }catch(Exception e){
            }
        }

        if(article.getKeyword() != null){
            try{
                doc.add(new TextField("keyword", article.getKeyword(), Field.Store.YES));
            }catch(Exception e){
            }
        }

        if(article.getContent() != null){
            try{
                doc.add(new TextField("content", article.getContent(), Field.Store.NO));
            }catch(Exception e){
            }
        }

        doc.add(new IntField("publishtime", Integer.parseInt(DateTools.dateToString(article.getPublishtime(), DateTools.Resolution.DAY)), Field.Store.YES));

        if (article.getFilename() != null)
            doc.add(new StringField("filename", article.getFilename(), StringField.Store.YES));
        doc.add(new StringField("dirname", article.getDirName(), StringField.Store.YES));

        return doc;
    }

    public DBDocument() {

    }
}
