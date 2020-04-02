package com.bizwink.webapps.collection;

import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CollectionPeer implements ICollectionManager{
    PoolServer cpool;

    public CollectionPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static ICollectionManager getInstance() {
        return CmsServer.getInstance().getFactory().getCollectionManager();
    }

    public int addCollectionInfo(Collection collection) throws CollectionException {

        return 0;
    }

    private static String CREATE_SIMPLE_COLLECTION_FOR_ORACLE = "insert into tbl_simplecollectioninfo(siteid, title, content, pubflag, articleid,id) values (?, ?, ?, ?, ?, ?)";

    private static String CREATE_SIMPLE_COLLECTION_FOR_MSSQL = "insert into tbl_simplecollectioninfo(siteid, title, content, pubflag, articleid) values (?, ?, ?, ?, ?)";

    private static String CREATE_SIMPLE_COLLECTION_FOR_MYSQL = "insert into tbl_simplecollectioninfo(siteid, title, content, pubflag, articleid) values (?, ?, ?, ?, ?)";

    public int addCollectionInfo(SimpleCollection collection) throws CollectionException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int errcode = 0;
        ISequenceManager sequenceManager = SequencePeer.getInstance();
            try {
                int id = sequenceManager.getSequenceNum("SelfDefine");
                System.out.println("id==" + id);
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                if (cpool.getType().equals("oracle")) {
                    System.out.println(CREATE_SIMPLE_COLLECTION_FOR_ORACLE);
                    pstmt = conn.prepareStatement(CREATE_SIMPLE_COLLECTION_FOR_ORACLE);
                }else if (cpool.getType().equals("mysql"))
                    pstmt = conn.prepareStatement(CREATE_SIMPLE_COLLECTION_FOR_MYSQL);
                else
                    pstmt = conn.prepareStatement(CREATE_SIMPLE_COLLECTION_FOR_MSSQL);
                pstmt.setInt(1,collection.getSiteid());
                pstmt.setString(2, collection.getTitle());
                pstmt.setString(3, collection.getContent());
                pstmt.setInt(4, collection.getPubflag());
                pstmt.setInt(5,collection.getArticleid());
                pstmt.setInt(6, id);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                errcode = -1;
                try {
                    conn.rollback();
                } catch (SQLException exp) {

                }
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }

        return errcode;
    }
}
