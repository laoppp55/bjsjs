package com.bizwink.cms.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kang on 2019/9/18.
 * 将“来信选登”数据插入文章表
 */
public class HTMLzmhd {

    public static void getArticleList(){
        System.out.println("启动政民互动接口");
        Connection conn = null;
        PreparedStatement pstmt;
        PreparedStatement pstmt1;
        ResultSet rs;
        ResultSet rs1;
        Article article;
        List list = new ArrayList();

        try{
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }

            //String url="jdbc:oracle:thin:@192.166.96.6:1521:oracle10g"; //正式环境
            String url="jdbc:oracle:thin:@localhost:1521:orcl";  //本机环境
            String UserName = "webbuildercms";
            String Password = "bizwinkcms";
            conn = DriverManager.getConnection(url, UserName, Password);
            System.out.println("Connection Oracle Successful!");
            conn.setAutoCommit(false);

            String getsql="select t.id as t1, t.title as maintitle ,t.CONTENT as content,t.REPLYTIME as publishtime,t.CREATEDATE as createdate from TBL_LETTER t where t.SELECTEDFLAG = 1 and t.STATUSFLAG= 1  "+
                    // " and to_date(to_char(t.publishtime,'yyyy/MM/dd'),'yyyy/MM/dd')=to_date(to_char(SYSDATE,'yyyy/MM/dd'),'yyyy/MM/dd')-1 "+ //查找前一天的文章
                    " and to_date(to_char(t.REPLYTIME,'yyyy/MM/dd'),'yyyy/MM/dd')=to_date(to_char(SYSDATE,'yyyy/MM/dd'),'yyyy/MM/dd')"+  //查找当天的
                    " order by t.REPLYTIME desc";

            pstmt = conn.prepareStatement(getsql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                article = new Article();
                article.setT1(rs.getInt("t1"));
                article.setMainTitle(rs.getString("maintitle"));
                article.setContent(rs.getString("content"));
                article.setPublishTime(rs.getTimestamp("publishtime"));
                article.setCreateDate(rs.getTimestamp("createdate"));
                list.add(article);
            }
            rs.close();
            pstmt.close();
            System.out.println("来信："+list.size());

            for(int i=0;i<list.size();i++){
                Article article1=(Article)list.get(i);
                String source = article1.getMainTitle();
                String Content = article1.getContent();

                String dirname = "/zmhd/lxxd/";
                int columnid=174;
                int status = 1;
                int siteid =40;
                String param = "&id="+article1.getT1();
                //String param = "&id=" + 8641;
                String otherUrl="/zmhd/lxxd/letter.shtml?"+BtoaEncode.botaEncodePassword(EncodingUtil.encodeURIComponent(param));
                //System.out.println(otherUrl);



               /* pstmt1 = conn.prepareStatement("select dirname,siteid from tbl_column where id= ?");
                pstmt1.setInt(1,columnid);
                rs1 = pstmt1.executeQuery();
                while(rs1.next()){
                    dirname = rs1.getString("dirname");
                }
                rs1.close();
                pstmt1.close();*/

               // dirname = "/zmhd/lxxd/letterlist.shtml";
                int t1 = 0;
                int nextID = 0;
                article = (Article)list.get(i);
                pstmt1 = conn.prepareStatement("select id from tbl_article where t1=? and columnid="+columnid);//判断 t1 是否重复
                pstmt1.setInt(1,article.getT1());
                rs1 = pstmt1.executeQuery();
                while (rs1.next()) {
                    t1 = rs1.getInt(1);
                }
                rs1.close();
                pstmt1.close();
                pstmt1 = conn.prepareStatement("select tbl_article_id.NEXTVAL from dual");
                rs1 = pstmt1.executeQuery();
                if (rs1.next())
                    nextID = rs1.getInt(1);
                rs1.close();
                pstmt1.close();
                if(t1 > 0){
                    System.out.println("已存在");
                }else{
                    System.out.println("插入一条记录--nextID = " + nextID);
                    pstmt = conn.prepareStatement("insert into TBL_ARTICLE(columnid,maintitle,vicetitle,summary,keyword,source,content,author,publishtime,defineurl,t1,siteid,emptycontentflag,createdate,lastupdated,dirname,editor,status,doclevel,pubflag,auditflag,subscriber,lockstatus,ispublished,indexflag,isjoinrss,clicknum,referID,modelID,bigpic,id,brand,filename,urltype)" +
                            " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    pstmt.setInt(1,columnid);
                    pstmt.setString(2,article.getMainTitle());
                    pstmt.setString(3,article.getViceTitle());
                    pstmt.setString(4,article.getSummary());
                    pstmt.setString(5,article.getKeyword());
                    pstmt.setString(6,source);
                    pstmt.setString(7,Content);
                    pstmt.setString(8,article.getAuthor());
                    pstmt.setTimestamp(9,article.getPublishTime());
                    pstmt.setString(10,otherUrl);
                    pstmt.setInt(11,article.getT1());
                    pstmt.setInt(12,siteid);
                    pstmt.setInt(13,0);
                    pstmt.setTimestamp(14,article.getCreateDate());
                    pstmt.setTimestamp(15,article.getCreateDate());
                    pstmt.setString(16,dirname);
                    pstmt.setString(17,"政民互动");
                    pstmt.setInt(18,status);
                    pstmt.setInt(19,0);
                    pstmt.setInt(20,0); //1 新稿 0 已发布 此为已发布
                    pstmt.setInt(21,0);
                    pstmt.setInt(22,0);
                    pstmt.setInt(23,0);
                    pstmt.setInt(24,0);
                    pstmt.setInt(25,0);
                    pstmt.setInt(26,0);
                    pstmt.setInt(27,0);
                    pstmt.setInt(28,0);
                    pstmt.setInt(29,0);
                    pstmt.setString(30,article.getProductBigPic());
                    pstmt.setInt(31,nextID);
                    pstmt.setString(32,article.getBrand());
                    pstmt.setString(33,article.getFileName());
                    pstmt.setInt(34, 1); //1外部链接  0 本站链接
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
                    pstmt.setInt(1, 39);
                    pstmt.setInt(2, columnid);
                    pstmt.setInt(3, nextID);
                    pstmt.setString(4, "政民互动");
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
            e.printStackTrace();
        }finally {
            if(conn !=null){
                try{
                    conn.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public void  ReadZmhd() throws Exception {
        getArticleList();
    }

    public static void main(String[] args) {
            getArticleList();
     }

}
