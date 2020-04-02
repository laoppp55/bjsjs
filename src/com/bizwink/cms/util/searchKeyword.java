package com.bizwink.cms.util;

import java.sql.*;

/**
 * Created by kang on 2019/2/14.
 */
public class searchKeyword {


    public static void Sqlquery(String keyword){
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Article article;

        try {
                try {
                    //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

              /*  String url="jdbc:oracle:thin:@127.0.0.1:1521:orcl"; //测试数据库
                String UserName = "webbuildercms";
                String Password = "bizwinkcms";*/
                String url="jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = 192.166.58.202)(PORT = 1521))(ADDRESS = (PROTOCOL = TCP)(HOST = 192.166.58.203)(PORT = 1521)) (LOAD_BALANCE = YES)(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = bjethnic)))";  //正式环境
                String UserName = "mwdbadmin";
                String Password = "qazwsxokm";

                conn = DriverManager.getConnection(url, UserName, Password);

                System.out.println("Connection Successful!");
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement("select id,maintitle,content,dirname,createdate from tbl_article");
                rs = pstmt.executeQuery();
                while(rs.next()) {
                    int id = rs.getInt("id");
                    String maintitle = rs.getString("maintitle");
                    String content = rs.getString("content");
                    String dirname = rs.getString("dirname");

                    String createdate_path = rs.getTimestamp("createdate").toString().substring(0, 10);
                    createdate_path = createdate_path.replaceAll("-", "");


                   // String href = "http://www.bjsjs.gov.cn";
                    String href = "http://www.bjethnic.gov.cn";
                    if(maintitle != null ){
                        if (maintitle.indexOf(keyword) != -1) {
                            //System.out.println("标题含有关键字");
                            href = href+dirname+"/"+createdate_path+"/"+id+".shtml";
                            System.out.println(href);
                        }else{
                            if(content != null){
                                if (content.indexOf(keyword) != -1) {
                                    //System.out.println("内容含有关键字");
                                    href = href+dirname+createdate_path+"/"+id+".shtml";
                                    System.out.println(href);
                                }
                            }
                        }
                    }

                }
                rs.close();
                pstmt.close();
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

    public static void main(String[] argv) {
        Sqlquery("吕锡文");

    }
}
