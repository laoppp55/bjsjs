package com.bizwink.cms.util;

import org.dom4j.*;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kang on 2018/8/30.
 */
public class XMLSjsdzb {
    private Document document = null;
    public XMLSjsdzb(String url){
        this.document = SourceXML.getDocument(url);
    }

    public XMLSjsdzb() {
    }

    public Document getDocument(){
        return     document;
    }
    public Element getRootElement() {
        /*if (document !=null)      {
             return document.getRootElement();
        }else{
            return null;
        }*/
        return getDocument().getRootElement();
    }

    public void traversalDocumentByIterator() {
        Element root = getRootElement();
        Article article;
        List list = new ArrayList();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        // 枚举根节点下所有子节点
        try {

                for (Iterator ie = root.elementIterator(); ie.hasNext(); ) {
                    Element element = (Element) ie.next();
                    // System.out.println(element.getName());
                    article = new Article();
                    // 枚举属性
                    for (Iterator ia = element.attributeIterator(); ia.hasNext(); ) {
                        Attribute attribute = (Attribute) ia.next();
                        //System.out.println(attribute.getName() + ":" + attribute.getData());
                        //article.setT1((Integer.parseInt((String)attribute.getData().toString().substring(0,5))));
                        //article.setT1((Integer.parseInt((String)attribute.getData())));
                    }
                    // 枚举当前节点下所有子节点
                    for (Iterator ieson = element.elementIterator(); ieson.hasNext(); ) {
                        Element elementSon = (Element) ieson.next();
                        for (Iterator ieson1 = elementSon.elementIterator(); ieson1.hasNext(); ) {
                            Element elementSon1 = (Element) ieson1.next();
                            if (elementSon1.getName().equals("pa_articles")) {
                                for (Iterator iterator = elementSon1.elementIterator(); iterator.hasNext(); ) {
                                    Element elementSon2 = (Element) iterator.next();
                                    article = new Article();
                                    for (Iterator iterator1 = elementSon2.elementIterator(); iterator1.hasNext(); ) {
                                        Element elementSon3 = (Element) iterator1.next();
                                        // System.out.println(elementSon3.getName() + ":" + elementSon3.getText());
                                        String xmlname = elementSon3.getName();
                                        String xmltext = elementSon3.getText();
                                        if (xmlname.equals("a_id")) {
                                            article.setT1(Integer.valueOf(xmltext));
                                        }
                                        if (xmlname.equals("a_name")) {
                                            article.setMainTitle(xmltext);
                                        }
                                        if (xmlname.equals("a_date")) {
                                            article.setPublishTime(Timestamp.valueOf(xmltext));
                                        }
                                        if (xmlname.equals("a_content")) {
                                            article.setContent(xmltext.trim().replace("&amp;", "&"));
                                        }
                                        if (xmlname.equals("a_url")) {
                                            article.setOtherurl(xmltext);
                                        }
                                        article.setMarkid("石景山电子报");
                                        article.setSiteID(199);  //正式环境
                                        //article.setSiteID(40); //测试环境


                                    }
                                    list.add(article);
                                }
                            }
                        }
                    }

                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Sqlquery(list);
        System.out.println("list = " + list.size());
    }




    public void Sqlquery(List list){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH )+1;
        int day = cal.get(Calendar.DATE);
        Connection conn = null;
        PreparedStatement pstmt;
        PreparedStatement pstmt1;
        PreparedStatement pstmt2;
        ResultSet rs;
        ResultSet rs1;

        try {
            try {
                //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            //String url="jdbc:oracle:thin:@127.0.0.1:1521:orcl"; //测试数据库
            String url="jdbc:oracle:thin:@192.166.96.6:1521:oracle10g";

            String UserName = "webbuildercms";
            String Password = "bizwinkcms";
           /* FileProps props = new FileProps("config.properties");
            String url = props.getProperty("db.url");
            String UserName = props.getProperty("db.username");
            String Password = props.getProperty("db.password");*/
            conn = DriverManager.getConnection(url, UserName, Password);

            System.out.println("Connection Successful!");
            conn.setAutoCommit(false);

            for(int i = 0; i < list.size();i++ ){
                int t1 = 0;
                int nextID = 0;
                Article article = (Article)list.get(i);
                article.setUrltype(0);
                article.setArticlepic("");
                int columnid = sqlMap(article.getMarkid());   //通过columnname  判断栏目ID

                if(columnid == 0){
                    continue;
                }
                if(columnid == 4001){ //3921 测试环境
                    article.setSource("石景山电子报");
                }

                pstmt1 = conn.prepareStatement("select id from tbl_article where t1=? and columnid=?");//判断 t1 是否重复
                pstmt1.setInt(1,article.getT1());
                pstmt1.setInt(2,columnid);
                rs1 = pstmt1.executeQuery();
                while (rs1.next()) {
                    t1 = rs1.getInt(1);
                }
                rs1.close();
                pstmt1.close();


                pstmt1 = conn.prepareStatement("select tbl_article_id.NEXTVAL from dual");
                rs1 = pstmt1.executeQuery();
                if (rs1.next()) nextID = rs1.getInt(1);
                rs1.close();
                pstmt1.close();

                String dirname = "";
                pstmt1 = conn.prepareStatement("select dirname from tbl_column where id= ?");
                pstmt1.setInt(1,columnid);
                rs1 = pstmt1.executeQuery();
                while(rs1.next()){
                    dirname = rs1.getString("dirname");
                    //System.out.println(dirname);
                }
                rs1.close();
                pstmt1.close();

                if(t1 > 0){
                    System.out.println("如果重复执行--update");
                   /* if(columnid ==685){
                        break;
                    }
                    pstmt2 = conn.prepareStatement("UPDATE TBL_ARTICLE SET maintitle=?,vicetitle=?,summary=?,keyword=?,source=?,content=?,author=?," +
                            "defineurl=?,mediafile=?,pubflag=?,urltype=?,bigpic=?,articlepic=? WHERE t1 = ? and columnid=?");

                    System.out.println("maintitle = "+ article.getMainTitle());
                    pstmt2.setString(1,article.getMainTitle());
                    pstmt2.setString(2,article.getViceTitle());
                    pstmt2.setString(3,article.getSummary());
                    pstmt2.setString(4,article.getKeyword());
                    pstmt2.setString(5,article.getSource());
                    //System.out.println("content :" + article.getContent().length());
                    pstmt2.setString(6,article.getContent());

                    pstmt2.setString(7,article.getAuthor());
                   // pstmt2.setTimestamp(8,article.getPublishTime());
                    pstmt2.setString(8,article.getOtherurl());
                    pstmt2.setString(9,article.getProductPic());
                    pstmt2.setInt(10,1);
                    pstmt2.setInt(11,article.getUrltype());
                    pstmt2.setString(12,article.getArticlepic());
                    pstmt2.setString(13,article.getArticlepic());
                    pstmt2.setInt(14,article.getT1());
                    pstmt2.setInt(15,columnid);
                    pstmt2.executeUpdate();
                    pstmt2.close();*/
                }else{
                    System.out.println("插入一条记录--nextID = " + nextID);
                    pstmt = conn.prepareStatement("insert into TBL_ARTICLE(columnid,maintitle,vicetitle,summary,keyword,source,content,author,publishtime,defineurl,t1,siteid,emptycontentflag,createdate,lastupdated,dirname,editor,status,doclevel,pubflag,auditflag,subscriber,lockstatus,ispublished,indexflag,isjoinrss,clicknum,referID,modelID,bigpic,id,mediafile,articlepic,urltype)" +
                            " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    pstmt.setInt(1,columnid);
                    pstmt.setString(2,article.getMainTitle());
                    pstmt.setString(3,article.getViceTitle());
                    pstmt.setString(4,article.getSummary());
                    pstmt.setString(5,article.getKeyword());
                    pstmt.setString(6,article.getSource());
                    //pstmt.setString(7,article.getContent());

                    DBUtil.setBigString("oracle", pstmt, 7, article.getContent());
                    pstmt.setString(8, article.getAuthor());
                    pstmt.setTimestamp(9, article.getPublishTime());
                    pstmt.setString(10, article.getOtherurl());
                    pstmt.setInt(11, article.getT1());
                    pstmt.setInt(12, article.getSiteID());
                    pstmt.setInt(13,0);
                    pstmt.setTimestamp(14,article.getPublishTime());
                    pstmt.setTimestamp(15,article.getPublishTime());
                    pstmt.setString(16,dirname);
                    //pstmt.setString(17,article.getAuthor());
                    pstmt.setString(17,"石景山电子报");
                    pstmt.setInt(18,1);
                    pstmt.setInt(19,0);
                    pstmt.setInt(20,1);
                    pstmt.setInt(21,0);
                    pstmt.setInt(22,1);
                    pstmt.setInt(23,0);
                    pstmt.setInt(24,0);
                    pstmt.setInt(25,0);
                    pstmt.setInt(26,0);
                    pstmt.setInt(27,0);
                    pstmt.setInt(28,0);
                    pstmt.setInt(29,0);
                    pstmt.setString(30,article.getProductPic());
                    pstmt.setInt(31,nextID);
                    pstmt.setString(32,article.getProductPic());
                    pstmt.setString(33,article.getArticlepic());
                    pstmt.setInt(34,article.getUrltype());
                    pstmt.executeUpdate();
                    pstmt.close();


                    //创建LOG
                    Calendar c = Calendar.getInstance();
                    int years = c.get(Calendar.YEAR) - 1900;
                    int months = c.get(Calendar.MONTH);
                    int dates = c.get(Calendar.DATE);

                    int logid = 0;
                    pstmt1 = conn.prepareStatement("select tbl_log_id.NEXTVAL from dual");
                    rs1 = pstmt1.executeQuery();
                    if (rs1.next()) logid = rs1.getInt(1);
                    rs1.close();
                    pstmt1.close();

                    String SQL_CREATE_LOG_FOR_ORACLE = "INSERT INTO tbl_log (SiteID,ColumnID,ArticleID,Editor,ActType,ActTime,MainTitle,createdate,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_ORACLE);
                    pstmt.setInt(1, 199);
                    pstmt.setInt(2, columnid);
                    pstmt.setInt(3, nextID);
                    pstmt.setString(4, "石景山电子报");
                    pstmt.setInt(5, 1);
                    pstmt.setTimestamp(6, article.getPublishTime());
                    pstmt.setString(7, article.getMainTitle());
                    pstmt.setDate(8, new Date(years, months, dates));
                    //System.out.println("logid=" + logid);
                    pstmt.setLong(9, logid);
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }
            conn.commit();
        }catch (SQLException e){
            System.out.println("i = 异常" + e);
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int sqlMap(String key){
        int code = 0;
        Map<String,String> map = new HashMap<String,String>();
        map.put("石景山电子报","4001");
        Iterator it = map.entrySet().iterator() ;
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next() ;
            if(entry.getKey().equals(key)){
                code = Integer.parseInt((String)entry.getValue());
            }
        }
        if(code == 0)
            System.out.println("columnname = " + key);
        return code;
    }

    public void traversalDocumentByVisitor() {
        getDocument().accept(new MyVisitor());
    }

    /**
     * 定义自己的访问者类
     */
    private static class MyVisitor extends VisitorSupport {
        /**
         * 对于属性节点，打印属性的名字和值
         */
        public void visit(Attribute node) {
            System.out.println("attribute : " + node.getName() + " = "
                    + node.getValue());
        }

        /**
         * 对于处理指令节点，打印处理指令目标和数据
         */
        public void visit(ProcessingInstruction node) {
            System.out.println("PI : " + node.getTarget() + " "
                    + node.getText());
        }

        /**
         * 对于元素节点，判断是否只包含文本内容，如是，则打印标记的名字和 元素的内容。如果不是，则只打印标记的名字
         */
        public void visit(Element node) {
            if (node.isTextOnly())
                System.out.println("element : " + node.getName() + " = "
                        + node.getText());
            else
                System.out.println("&&&&**********" + node.getName() + "--------");
        }
    }

    private  void xmlToolkit(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH )+1;
        int day = cal.get(Calendar.DATE);
        System.out.println(year + " 年 " + month + " 月"+day+"日");
        System.out.println("访问石景山电子报接口：");
        List<String> list=new ArrayList<String>();
        list.add("http://m1.cuepa.cn/content_list.php");  //石景山电子报  4001

        for (String string : list) {
            System.out.println(string);
            XMLSjsdzb dom4jParser = new XMLSjsdzb(string);
            dom4jParser.traversalDocumentByIterator();
        }
    }




    public static void main(String[] argv) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH )+1;
        int day = cal.get(Calendar.DATE);
        System.out.println(year + " 年 " + month + " 月"+day+"日");
        System.out.println("访问石景山电子报接口：");
        List<String> list=new ArrayList<String>();
        list.add("http://m1.cuepa.cn/content_list.php");  //石景山电子报  4001

        for (String string : list) {
            System.out.println(string);
            XMLSjsdzb dom4jParser = new XMLSjsdzb(string);
            dom4jParser.traversalDocumentByIterator();
        }

    }

}
