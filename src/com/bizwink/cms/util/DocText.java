package com.bizwink.cms.util;


import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

public class DocText {

    public static void main(String[] args) throws Exception, IOException {
        readfile("D:\\docs\\");
    }

    public static boolean readfile(String filepath) throws FileNotFoundException, IOException {
        try {

            File file = new File(filepath);
            if (!file.isDirectory()) {
                //System.out.println("文件");
                 //System.out.println("path=" + file.getPath());
                 //System.out.println("absolutepath=" + file.getAbsolutePath());
                 //System.out.println("name=" + file.getName());

            } else if (file.isDirectory()) {
                //System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                         System.out.println("path=" + readfile.getPath());
                         System.out.println("absolutepath="+ readfile.getAbsolutePath());
                         System.out.println(i+"=name=" + readfile.getName());

                        readfiles(readfile.getAbsolutePath());
                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i]);
                    }
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return true;
    }

    public static void readfiles(String filepath) throws FileNotFoundException, IOException {
        /*String BASE_PATH = "D:\\doc\\";
        File file = new File(BASE_PATH + "北京市政民互动市民来信处理单zm1519223304894.doc");
        File file1 = new File(BASE_PATH + "北京市政民互动市民来信处理单zm1516327187463.doc");
        File file2 = new File(BASE_PATH + "北京市政民互动市民来信处理单zm1541401856617.doc");
        File file3 = new File(BASE_PATH + "北京市政民互动市民来信处理单zm1535877982287.doc");
        File file4 = new File(BASE_PATH + "北京市政民互动市民来信处理单zm1530548164850.doc");
        File file5 = new File(BASE_PATH + "北京市政民互动市民来信处理单zm1515834697234.doc");
        */
        File file6 = new File(filepath);
        HWPFDocument doc = new HWPFDocument(new FileInputStream(file6));
        //System.out.printf(doc.getDocumentText());


        //通过Range对象获取Text
        Range range = doc.getRange();
        //System.out.println("rang获取text"+text);

        //获取段落数目
        //在Word中，一个回车符就是一个段落了
        int nums = range.numParagraphs();
        // System.out.println("段落="+nums);
        Letter letter = new Letter();
        // StringBuffer buf = new StringBuffer();
        String reStr = "";
        for (int i = 0; i < nums; i++) {
            //System.out.println("段落" + i + "，内容为：" + range.getParagraph(i).text());
            if (i == 6) //old编码
                letter.setOldcode(range.getParagraph(i).text());
            if (i== 9)//信件类型
                letter.setCname(range.getParagraph(i).text().trim());
            if (i == 18) //标题
                letter.setTitle(range.getParagraph(i).text().trim());
            if (i == 21) //内容
                letter.setContent(range.getParagraph(i).text());
            if (i == 24) {
                String str = range.getParagraph(i).text();
                String[] strarray = str.split(" ");
                for (int j = 0; j < strarray.length; j++) {
                    if (j == 0) //写信人
                        letter.setLinkman(strarray[j]);
                    if (j == 1) //电话
                        letter.setPhone(strarray[j]);
                }
            }
            if (i == 27) {//写信时间
                String str = range.getParagraph(i).text().trim();
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                String tsStr = str + " 00:00:00";
                try {
                    ts = Timestamp.valueOf(tsStr);
                    // System.out.println(ts);
                    letter.setCreatedate(ts);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //System.out.println(date);
                //letter.setCreatedate(date);
            }
            if (i > 39) {
                String temStr=range.getParagraph(i).text();
                if(temStr.length()!=1)
                    reStr = reStr + temStr ;
                //reStr = reStr + "<p>" + range.getParagraph(i).text().trim() + "</p>";
            }
        }
        //System.out.println(reStr);
        letter.setReplycontent(reStr);
        insert(letter);
    }

    public static void insert(Letter letter) {
        //System.out.println(letter.getReplycontent());

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
            conn = DriverManager.getConnection(url, UserName, Password);

            System.out.println("Connection Successful!");
            conn.setAutoCommit(false);

            int t1 = 0;
            int nextID = 0;
            int categorycode=0;
            String oldcode = letter.getOldcode();
            pstmt1 = conn.prepareStatement("select id from tbl_letter where oldcode=?");//判断 oldcode 是否重复
            pstmt1.setString(1, oldcode);
            rs1 = pstmt1.executeQuery();
            while (rs1.next()) {
                t1 = rs1.getInt(1);
            }
            rs1.close();
            pstmt1.close();

            pstmt1 = conn.prepareStatement("select tbl_letter_id.NEXTVAL from dual");
            rs1 = pstmt1.executeQuery();
            if (rs1.next()) nextID = rs1.getInt(1);
            rs1.close();
            pstmt1.close();

            String cname = letter.getCname();
            if(cname != null && cname.length()>0) {
                pstmt1 = conn.prepareStatement("select id from tbl_letter_type where typename=?");
                pstmt1.setString(1, cname);
                rs1 = pstmt1.executeQuery();
                if (rs1.next()) categorycode = rs1.getInt(1);
                rs1.close();
                pstmt1.close();
            }




            if (t1 > 0) {
                System.out.println("已存在");
            } else {
                System.out.println("插入一条记录--nextID = " + nextID);
                pstmt = conn.prepareStatement("insert into tbl_letter" +
                        "(id,title,content,categorycode,publishflag,createdate,replycontent,replytime,statusflag,newflag,searchmsg,linkman,phone,oldcode)" +
                        " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pstmt.setInt(1,nextID);
                pstmt.setString(2, letter.getTitle());
                pstmt.setString(3,letter.getContent());
                pstmt.setBigDecimal(4, BigDecimal.valueOf(categorycode));
                pstmt.setBigDecimal(5, BigDecimal.valueOf(0));
                pstmt.setTimestamp(6, Timestamp.valueOf(letter.getCreatedate().toString()));
                pstmt.setString(7, letter.getReplycontent());
                pstmt.setTimestamp(8, Timestamp.valueOf(letter.getCreatedate().toString()));
                pstmt.setBigDecimal(9, BigDecimal.valueOf(0));
                pstmt.setBigDecimal(10,BigDecimal.valueOf(0));
                pstmt.setString(11, getSearchmsgs());
                pstmt.setString(12,letter.getLinkman());
                pstmt.setString(13,letter.getPhone());
                pstmt.setString(14,oldcode);
                pstmt.executeUpdate();
                pstmt.close();
            }
            conn.commit();
        } catch (SQLException e) {
            System.out.println("i = 异常" + e);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static String getSearchmsgs(){
        String result="";
        String a = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] rands = new char[6];
        for (int i = 0; i < rands.length; i++)
        {
            int rand = (int) (Math.random() * a.length());
            rands[i] = a.charAt(rand);
        }
        for(int i=0;i<rands.length;i++){
            result += rands[i];
        }
        return result;
    }
}