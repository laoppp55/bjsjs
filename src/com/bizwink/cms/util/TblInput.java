package com.bizwink.cms.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kang on 2019/4/10.
 */
public class TblInput {
    public TblInput() {
    }

    private static  void TblInput(){
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Wz wz;
        Wz wz3;

        List list;
        List list1;
        List list2;
        List list3 = null;
        List list4 = null;
        try {
            try {
                //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            String url="jdbc:oracle:thin:@localhost:1522:orcl11g";
            String UserName = "coositedb";
            String Password = "coositedbpass2009";
            conn = DriverManager.getConnection(url, UserName, Password);
            System.out.println("Connection Successful!");
            conn.setAutoCommit(false);

            //取出第一级parentid
            String getparentidsql="select t.id from tbl_companyclass t where t.siteid=5871 and t.parentid=1501";
            pstmt = conn.prepareStatement(getparentidsql);
            rs = pstmt.executeQuery();
            list2 = new ArrayList();
            while(rs.next()){
                list2.add(rs.getInt("id"));
            }
            rs.close();
            pstmt.close();

            //取第二级的parentid
            for(int m=0; m< list2.size();m++){
                int pid2 = (Integer) list2.get(m);
                String getparentidsql2="select t.id from tbl_companyclass t where t.siteid=5871 and t.parentid=?";
                pstmt = conn.prepareStatement(getparentidsql2);
                pstmt.setInt(1, pid2);
                rs = pstmt.executeQuery();
                list3 = new ArrayList();
                while(rs.next()){
                    list3.add(rs.getInt("id"));
                }
                rs.close();
                pstmt.close();


            //读取第三级 parentid
            for(int n=0; n< list3.size(); n++){
                int pid3 = (Integer) list3.get(n);
                String getparentidsql3="select t.id from tbl_companyclass t where t.siteid=5871 and t.parentid=?";
                pstmt = conn.prepareStatement(getparentidsql3);
                pstmt.setInt(1, pid3);
                rs = pstmt.executeQuery();
                list4 = new ArrayList();
                while(rs.next()){
                    list4.add(rs.getInt("id"));
                }
                rs.close();
                pstmt.close();





            //根据第三级的parentid,读取ename
            for(int k=0; k < list4.size();k++) {
                int pid = (Integer) list4.get(k);
                String getparentsql = "select t.ename,t.dirname from tbl_companyclass t where t.siteid=5871 and t.parentid=?";
                pstmt = conn.prepareStatement(getparentsql);
                pstmt.setInt(1, pid);
                rs = pstmt.executeQuery();

                list1 = new ArrayList();
                while (rs.next()) {
                    wz3 = new Wz();
                    wz3.setEname(rs.getString("ename"));
                    wz3.setDirname(rs.getString("dirname"));
                    list1.add(wz3);
                }
                rs.close();
                pstmt.close();


                //去源表读取数据
                for (int j = 0; j < list1.size(); j++) {
                    Wz wz2 = (Wz) list1.get(j);
                    String getsql = "select t.name,t.code,t.pcode from tbl_wzclass t where pcode =?";
                    pstmt = conn.prepareStatement(getsql);
                    pstmt.setString(1, wz2.getEname());
                    rs = pstmt.executeQuery();
                    list = new ArrayList();
                    while (rs.next()) {
                        wz = new Wz();
                        wz.setName(rs.getString("name"));
                        wz.setCode(rs.getString("code"));
                        wz.setPcode(rs.getString("pcode"));
                        list.add(wz);
                    }
                    rs.close();
                    pstmt.close();

                    for (int i = 0; i < list.size(); i++) {
                        Wz wz1 = (Wz) list.get(i);
                        int t1 = 0;
                        pstmt = conn.prepareStatement("select t.id from tbl_companyclass t where t.ename=?");
                        pstmt.setString(1, wz1.getCode());
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            t1 = rs.getInt(1);
                        }
                        rs.close();
                        pstmt.close();

                        if (t1 > 0) {
                            System.out.println("已存在");
                        } else {
                            int nextID = 0;
                            pstmt = conn.prepareStatement("select companyinfo_id.NEXTVAL from dual");
                            rs = pstmt.executeQuery();
                            if (rs.next())
                                nextID = rs.getInt(1);
                            rs.close();
                            pstmt.close();

                            int parentID = 0;
                            pstmt = conn.prepareStatement("select t.id from tbl_companyclass t where t.ename=?");
                            pstmt.setString(1, wz1.getPcode());
                            rs = pstmt.executeQuery();
                            while (rs.next()) {
                                parentID = rs.getInt(1);
                            }
                            rs.close();
                            pstmt.close();
                            System.out.println("插入一条记录--nextID = " + nextID + "  parentID= " + parentID);
                            pstmt = conn.prepareStatement("insert into tbl_companyclass(id,siteid,parentid,orderid,cname,ename,dirname,editor,extname,createdate,lastupdated)" +
                                    " values(?,?,?,?,?,?,?,?,?,?,?)");
                            pstmt.setInt(1, nextID);
                            pstmt.setInt(2, 5871);
                            pstmt.setInt(3, parentID);
                            pstmt.setInt(4, i);
                            pstmt.setString(5, wz1.getName());
                            pstmt.setString(6, wz1.getCode());
                            pstmt.setString(7, wz2.getDirname() + wz1.getCode() + "/");
                            pstmt.setString(8, "biztest3");
                            pstmt.setString(9, "shtml");
                            pstmt.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
                            pstmt.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
                            pstmt.executeUpdate();
                            pstmt.close();
                        }
                    }
                      }
                    }
                }
            }
            conn.commit();
        }catch (SQLException e){
            System.out.println("i = 异常" + e);
            e.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] argv) {
        TblInput();
    }
}
