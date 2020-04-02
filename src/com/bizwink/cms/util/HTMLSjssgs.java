package com.bizwink.cms.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kang on 2019/6/12.
 */
public class HTMLSjssgs {

    public void addInfo(String url)throws Exception{
        try {
            List<NameValuePair> param = new ArrayList<>();
            String s = HttpRequestEmsTest.postWithParamsForString(url, param);
            if(s!=null && !s.equals("该日期没有上报数据") && !s.equals("key密码不可为空") && !s.equals("key密码不匹配")){
                String json = DesUtil.decryptToString(s, "K1O2N3G4");
               // System.out.println(json);
                ResponseInfo responseInfo = JSON.parseObject(json, ResponseInfo.class);
                if(responseInfo.getCode().equals("1")) {
                    List<Article_sgs> date = responseInfo.getDate();
                    Sqlquery(date);

                   /* for (Article_sgs cur : date) {
                        System.out.println("许可决定文书名称=" + cur.getPerfile()); //行政许可决定文书名称
                        System.out.println("处罚决定书文号=" +cur.getDocnot()); //行政处罚决定书文号

                        System.out.println("许可相对人名称=" + cur.getRepername()); //行政许可相对人名称

                        System.out.println("许可机关=" + cur.getPermitorg()); //许可机关
                        System.out.println("处罚机关=" + cur.getPunorg());  //处罚机关

                        System.out.println("栏目编码=" + cur.getDepcode());  //栏目编码

                        System.out.println("许可详细编码="+cur.getPerid());
                        System.out.println("处罚详细编码=" + cur.getPunid());    //详细编码



                    }*/
                }

            }else {
                System.out.println(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Sqlquery(List<Article_sgs> list){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH )+1;
        int day = cal.get(Calendar.DATE);
        Connection conn = null;
        PreparedStatement pstmt;
        PreparedStatement pstmt1;
        PreparedStatement pstmt2;
        ResultSet rs1;

        try {
            try {
                //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            String url="jdbc:oracle:thin:@127.0.0.1:1521:orcl"; //测试数据库
            //String url="jdbc:oracle:thin:@192.166.96.6:1521:oracle10g"; //正式数据库
            // String url="jdbc:oracle:thin:@localhost:1521:orcl";
            String UserName = "webbuildercms";
            String Password = "bizwinkcms";
           /* FileProps props = new FileProps("config.properties");
            String url = props.getProperty("db.url");
            String UserName = props.getProperty("db.username");
            String Password = props.getProperty("db.password");*/
            conn = DriverManager.getConnection(url, UserName, Password);

            System.out.println("Connection Successful!");
            conn.setAutoCommit(false);
            Article article;
            for(int i = 0; i < list.size();i++ ){
                int t1=0;
                String vicetitle = "";
                int nextID = 0;
                Article_sgs article_sgs = (Article_sgs)list.get(i);
                article = new Article();
                article.setSiteID(40);
                article.setUrltype(1); //1外部链接  0 本站链接
                String otherUrl="";

                if(article_sgs.getPerfile() !=null){
                    article.setMainTitle("【行政许可】"+article_sgs.getPerfile());
                    otherUrl = "/es/es/DoublePublicity/permitDetail.do?depcode="+ article_sgs.getDepcode()+"&perid="+article_sgs.getPerid();
                    article.setKeyword(article_sgs.getRepername()+article_sgs.getPermitorg());
                    article.setSummary("行政许可决定文书名称:"+article_sgs.getPerfile()+" 行政许可相对人名称:"+article_sgs.getRepername()+" 许可机关:"+article_sgs.getPermitorg());
                    article.setContent("行政许可决定文书名称:"+article_sgs.getPerfile()+" 行政许可相对人名称:"+article_sgs.getRepername()+" 许可机关:"+article_sgs.getPermitorg());
                    vicetitle = article_sgs.getPerid();

                }
                if(article_sgs.getDocnot() != null){
                    article.setMainTitle("【行政处罚】"+article_sgs.getDocnot());
                    otherUrl = "/es/es/DoublePublicity/punishDetail.do?depcode="+ article_sgs.getDepcode()+"&punid="+article_sgs.getPunid();
                    article.setKeyword(article_sgs.getRepername()+article_sgs.getPunorg());
                    article.setSummary("行政处罚决定书文号:"+article_sgs.getDocnot()+" 行政相对人名称:"+article_sgs.getRepername()+" 处罚机关:"+article_sgs.getPunorg());
                    article.setContent("行政处罚决定书文号:"+article_sgs.getDocnot()+" 行政相对人名称:"+article_sgs.getRepername()+" 处罚机关:"+article_sgs.getPunorg());
                    vicetitle = article_sgs.getPunid();
                }
                article.setViceTitle(vicetitle);
                article.setOtherurl(otherUrl);
                article.setPublishTime(new Timestamp(System.currentTimeMillis()));


                int columnid = 3941; //测试栏目id
               // int columnid = 4101;  //正式



                pstmt1 = conn.prepareStatement("select id from tbl_article where VICETITLE=? and columnid=?");//判断 t1 是否重复
                pstmt1.setString(1, vicetitle);
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

                String dirname = "/es/es/DoublePublicity/findDepartList.do";
                /*pstmt1 = conn.prepareStatement("select dirname from tbl_column where id= ?");
                pstmt1.setInt(1,columnid);
                rs1 = pstmt1.executeQuery();
                while(rs1.next()){
                    dirname = rs1.getString("dirname");
                    //System.out.println(dirname);
                }
                rs1.close();
                pstmt1.close();*/

                if(t1 > 0){
                    System.out.println("如果重复执行--update");

                   /* pstmt2 = conn.prepareStatement("UPDATE TBL_ARTICLE SET maintitle=?,vicetitle=?,summary=?,keyword=?,source=?,content=?,author=?," +
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

                    com.bizwink.cms.util.DBUtil.setBigString("oracle", pstmt, 7, article.getContent());
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
                    pstmt.setString(17,"石景山信用双公示");
                    pstmt.setInt(18,1);
                    pstmt.setInt(19,0);
                    pstmt.setInt(20,0); //1 新稿 0 已发布 此为已发布
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
                    pstmt.setInt(1, 39);
                    pstmt.setInt(2, columnid);
                    pstmt.setInt(3, nextID);
                    pstmt.setString(4, "石景山信用双公示");
                    pstmt.setInt(5, 1);
                    pstmt.setTimestamp(6, article.getPublishTime());
                    pstmt.setString(7, article.getMainTitle());
                    pstmt.setDate(8, new java.sql.Date(years, months, dates));
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


    public void  ReadSgs() throws Exception {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateNowStr = sdf.format(d);
        String keycode = Encrypt.md5(dateNowStr.getBytes()).toLowerCase();
        String xzxkurl ="http://61.49.1.174/es/es/PortalNoLogined/PagePermit.do?key="+keycode; //行政许可
        String xzcfulr ="http://61.49.1.174/es/es/PortalNoLogined/PagePunish.do?key="+keycode; //行政处罚
        System.out.println("访问双公示接口：");
        System.out.println(xzxkurl);
        System.out.println(xzcfulr);

        List<String> list=new ArrayList<String>();
        list.add(xzxkurl);  //行政许可
        list.add(xzcfulr);  //行政处罚
        for (String string : list) {
            //System.out.println(string);
            HTMLSjssgs htmlSjssgs = new HTMLSjssgs();
            htmlSjssgs.addInfo(string);
        }

    }

    public static void main(String[] argv) throws Exception {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateNowStr = sdf.format(d);
        String keycode = Encrypt.md5(dateNowStr.getBytes()).toLowerCase();
        String xzxkurl ="http://61.49.1.174/es/es/PortalNoLogined/PagePermit.do?key="+keycode; //行政许可
        String xzcfulr ="http://61.49.1.174/es/es/PortalNoLogined/PagePunish.do?key="+keycode; //行政处罚
        System.out.println("访问双公示接口：");
        System.out.println(xzxkurl);
        System.out.println(xzcfulr);

        List<String> list=new ArrayList<String>();
        list.add(xzxkurl);  //行政许可
        list.add(xzcfulr);  //行政处罚
        for (String string : list) {
            //System.out.println(string);
            HTMLSjssgs htmlSjssgs = new HTMLSjssgs();
            htmlSjssgs.addInfo(string);
        }

    }


}
