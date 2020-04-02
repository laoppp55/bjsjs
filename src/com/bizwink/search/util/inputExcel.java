package com.bizwink.search.util;


import com.bizwink.search.domain.Archives;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class inputExcel {

    public inputExcel(){

    }


    public static Workbook getWeebWork(String filename) throws IOException {
        Workbook workbook=null;
        if(null!=filename){
            String fileType=filename.substring(filename.lastIndexOf("."),filename.length());
            FileInputStream fileStream = new FileInputStream(new File(filename));
            if(".xls".equals(fileType.trim().toLowerCase())){
                workbook = new HSSFWorkbook(fileStream);// 创建 Excel 2003 工作簿对象
            }else if(".xlsx".equals(fileType.trim().toLowerCase())){
                workbook = new XSSFWorkbook(fileStream);//创建 Excel 2007 工作簿对象
            }
        }

        return workbook;
    }

    public static String getInfolistFromExcel(String excelFilename) {
        int  numSheets = 0;
        org.apache.poi.ss.usermodel.Sheet aSheet = null;
        List<Archives> list = new ArrayList<Archives>();
        try {
            Workbook workbook = inputExcel.getWeebWork(excelFilename);
            aSheet = workbook.getSheetAt(numSheets);//获得一个sheet
            if (aSheet != null) {
                for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
                    Row aRow = aSheet.getRow(rowNumOfSheet); //获得一个行
                    Archives archives = new Archives();
                    for (short cellNumOfRow = 0; cellNumOfRow <= aRow.getLastCellNum(); cellNumOfRow++) {
                        String buf = "";
                        if (null != aRow.getCell(cellNumOfRow)) {
                            Cell aCell = aRow.getCell(cellNumOfRow);//获得列值
                            if (aCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                buf = aCell.getStringCellValue();
                                buf = buf.trim();
                            } else if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                                buf = String.valueOf((long)aCell.getNumericCellValue());
                            }

                        }

                        if(cellNumOfRow ==0){
                            archives.setArchivesid(buf.trim());
                        }
                        if(cellNumOfRow ==1){
                            Timestamp startdate=null;
                            Timestamp enddate=null;
                            if(buf.length()>=8){
                                // System.out.println(rowNumOfSheet+":"+buf);
                                if(buf.indexOf("-")!=-1) {
                                    String buf1 = buf.substring(0, buf.indexOf("-"));
                                    String buf2 = buf.substring(buf.indexOf("-") + 1);
                                    if (buf1.endsWith("0000")) {
                                        buf1 = buf1.substring(0, 4);
                                        startdate = toTimestamp(buf1, "yyyy");
                                    } else if (buf1.endsWith("00")) {
                                        buf1 = buf1.substring(0, 6);
                                        startdate = toTimestamp(buf1, "yyyyMM");
                                    } else {
                                        startdate = toTimestamp(buf1, "yyyyMMdd");
                                    }
                                    if (buf2.endsWith("0000")) {

                                        buf2 = buf2.substring(0, 4);
                                        enddate = toTimestamp(buf2, "yyyy");
                                    } else if (buf2.endsWith("00")) {
                                        buf2 = buf2.substring(0, 6);
                                        enddate = toTimestamp(buf2, "yyyyMM");
                                    } else {
                                        enddate = toTimestamp(buf2, "yyyyMMdd");
                                    }
                                }else{
                                    if (buf.endsWith("0000")) {
                                        buf = buf.substring(0, 4);
                                        startdate = toTimestamp(buf, "yyyy");
                                    } else if (buf.endsWith("00")) {
                                        buf = buf.substring(0, 6);
                                        startdate = toTimestamp(buf, "yyyyMM");
                                    } else {
                                        startdate = toTimestamp(buf, "yyyyMMdd");
                                    }
                                }

                            }else{

                                if(buf.indexOf("-")!=-1){
                                    startdate = toTimestamp(buf, "yyyy-MM");
                                }else{
                                    startdate = toTimestamp(buf, "yyyy");
                                }
                            }
                            archives.setStartdate(startdate);
                            archives.setEnddate(enddate);

                        }
                        if(cellNumOfRow ==2){
                            Timestamp enddate=null;
                            if(buf.indexOf("-")!=-1){
                                enddate= toTimestamp(buf, "yyyy-MM");
                            }else {
                                if (buf != null && buf.length() > 0) {
                                    // System.out.println(rowNumOfSheet + ":" + buf);
                                    if (buf.length() != 4) {
                                        if (buf.endsWith("0000")) {
                                            buf = buf.substring(0, 4);
                                            enddate = toTimestamp(buf, "yyyy");
                                        } else if (buf.endsWith("00")) {
                                            buf = buf.substring(0, 6);
                                            enddate = toTimestamp(buf, "yyyyMM");
                                        } else {
                                            enddate = toTimestamp(buf, "yyyyMMdd");
                                        }
                                    } else {
                                        enddate = toTimestamp(buf, "yyyy");
                                    }
                                }
                            }
                            if(archives.getEnddate()==null){

                                archives.setEnddate(enddate);
                            }
                        }
                        if(cellNumOfRow ==3){
                            archives.setArchivesname(buf.trim());
                        }

                    }
                    list.add(archives);
                }
                Sql(list);
            }

        } catch (IOException exp) {
            exp.printStackTrace();
        }

        return "";
    }

    private static void Sql(List list){
            Connection conn = null;
            PreparedStatement pstmt;
            PreparedStatement pstmt1;
            PreparedStatement pstmt2;
            ResultSet rs;
            ResultSet rs1;

            try{
                 try {
                //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                     Class.forName("oracle.jdbc.driver.OracleDriver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                /*String url="jdbc:oracle:thin:@116.90.87.164:1521:orcl10g";
                String UserName = "bjsjsdbadmin";
                String Password = "qazwsxokm";*/
                String url="jdbc:oracle:thin:@192.166.96.6:1521:oracle10g";
                String UserName = "webbuildercms";
                String Password = "bizwinkcms";
                conn = DriverManager.getConnection(url, UserName, Password);
                System.out.println("Connection Successful!");
                conn.setAutoCommit(false);
                Archives archives = new Archives();

                for(int i=0;i < list.size();i++){
                     archives = (Archives)list.get(i);
                     int nextID = 0;
                     pstmt1 = conn.prepareStatement("select tbl_archives_id.NEXTVAL from dual");
                     rs1 = pstmt1.executeQuery();
                     if (rs1.next()) {
                         nextID = rs1.getInt(1);
                     }
                    rs1.close();
                    pstmt1.close();
                    System.out.println("插入一条记录--nextID = " + nextID);
                    pstmt = conn.prepareStatement("insert into tbl_archives(id,archivesid,startdate,enddate,archivesname,createuser,createdate)" +
                            "values(?,?,?,?,?,?,?)");
                    pstmt.setInt(1,nextID);
                    pstmt.setString(2, archives.getArchivesid());
                    pstmt.setTimestamp(3, archives.getStartdate());
                    pstmt.setTimestamp(4,archives.getEnddate());
                    pstmt.setString(5, archives.getArchivesname());
                    pstmt.setString(6,"initialize");
                    pstmt.setTimestamp(7,new Timestamp(System.currentTimeMillis()));
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                 conn.commit();

        }catch(Exception e)  {
            e.printStackTrace();
            System.out.println("连接异常");
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

    public static String getInfolistFromExcels(String excelFilename) {
        int  numSheets = 0;
        org.apache.poi.ss.usermodel.Sheet aSheet = null;
        List<Archives> list = new ArrayList<Archives>();
        try {
            Workbook workbook = inputExcel.getWeebWork(excelFilename);
            aSheet = workbook.getSheetAt(numSheets);//获得一个sheet
            if (aSheet != null) {
                for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
                    Row aRow = aSheet.getRow(rowNumOfSheet); //获得一个行
                    Archives archives = new Archives();
                    for (short cellNumOfRow = 0; cellNumOfRow <= aRow.getLastCellNum(); cellNumOfRow++) {
                        String buf = "";
                        if (null != aRow.getCell(cellNumOfRow)) {
                            Cell aCell = aRow.getCell(cellNumOfRow);//获得列值
                            if (aCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                buf = aCell.getStringCellValue();
                                buf = buf.trim();
                            } else if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                                buf = String.valueOf((long)aCell.getNumericCellValue());
                            }

                        }
                        if(cellNumOfRow ==0) {
                            archives.setNum(buf.trim());

                        }
                        if(cellNumOfRow ==1) {
                            archives.setArchivesid(buf.trim());

                        }
                        if(cellNumOfRow ==2) {
                            archives.setArchivesname(buf.trim());

                        }
                        if(cellNumOfRow ==3) {
                            archives.setProductiondate(buf.trim());

                        }

                        if(cellNumOfRow ==6) {
                            archives.setIdentifyyear(buf.trim());
                        }



                    }
                    list.add(archives);
                }
                Sqls(list);
            }

        } catch (IOException exp) {
            exp.printStackTrace();
        }

        return "";
    }

    private static void Sqls(List list){
        Connection conn = null;
        PreparedStatement pstmt;
        PreparedStatement pstmt1;
        PreparedStatement pstmt2;
        ResultSet rs;
        ResultSet rs1;

        try{
            try {
                //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            String url="jdbc:oracle:thin:@192.166.96.6:1521:oracle10g";
            String UserName = "webbuildercms";
            String Password = "bizwinkcms";
           /* String url="jdbc:oracle:thin:@116.90.87.164:1521:orcl10g";
            String UserName = "bjsjsdbadmin";
            String Password = "qazwsxokm";*/
            conn = DriverManager.getConnection(url, UserName, Password);
            System.out.println("Connection Successful!");
            conn.setAutoCommit(false);
            Archives archives = new Archives();

            for(int i=0;i < list.size();i++){
                archives = (Archives)list.get(i);
                int nextID = 0;
                pstmt1 = conn.prepareStatement("select tbl_archives_id.NEXTVAL from dual");
                rs1 = pstmt1.executeQuery();
                if (rs1.next()) {
                    nextID = rs1.getInt(1);
                }
                rs1.close();
                pstmt1.close();
                System.out.println("插入一条记录--nextID = " + nextID);
                pstmt = conn.prepareStatement("insert into tbl_archivess(id,num,archivesid,productiondate,identifyyear,archivesname,createuser,createdate)" +
                        "values(?,?,?,?,?,?,?,?)");
                pstmt.setInt(1,nextID);
                pstmt.setString(2, archives.getNum());
                pstmt.setString(3, archives.getArchivesid());
                pstmt.setString(4, archives.getProductiondate());
                pstmt.setString(5, archives.getIdentifyyear());
                pstmt.setString(6, archives.getArchivesname());
                pstmt.setString(7,"initialize");
                pstmt.setTimestamp(8,new Timestamp(System.currentTimeMillis()));
                pstmt.executeUpdate();
                pstmt.close();
            }
            conn.commit();

        }catch(Exception e)  {
            e.printStackTrace();
            System.out.println("连接异常");
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

    private static  Timestamp toTimestamp(String str,String formatstr) {
        DateFormat format = new SimpleDateFormat(formatstr);
        //DateFormat format = new SimpleDateFormat("yyyy.MM");
        format.setLenient(false);
        //要转换字符串 str_test
        //String str_test = "2011.4";

        Timestamp ts = null;
        try {
            ts = new Timestamp(format.parse(str).getTime());
            //System.out.println(ts.toString());
        } catch (ParseException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ts;
    }


    public static void main(String[] args) {
        String path="D:\\123\\开放档案案卷级目录（2016前）.xls";
        getInfolistFromExcel(path);
        String paths="D:\\123\\开放档案目录（2016开放文件级）.xls";
        getInfolistFromExcels(paths);

    }
}
