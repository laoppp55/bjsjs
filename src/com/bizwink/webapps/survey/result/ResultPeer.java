package com.bizwink.webapps.survey.result;

import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.IFactory;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.webapps.survey.answer.Answer;
import com.bizwink.webapps.survey.define.Define;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultPeer
  implements IResultManager
{
  PoolServer cpool;
  private static String GET_ALL_SURVEY_USERS_BY_SURVEYID = "select distinct uid from su_answer where sid = ?";

  private static String GET_ALL_SURVEY_USERSNUM_BY_SURVEYID = "select count(distinct uid) as unum from su_answer where sid = ?";

  private String GET_A_QUESTION_RESULT_OF_USER = "SELECT a.answers FROM su_answer a INNER JOIN su_dquestion qa ON a.qid = qa.qid WHERE (a.qid = ?) AND (a.uid = ?) ORDER BY a.qid";

  private static String GET_ALL_DEFINE_QUESTIONSID = "SELECT qid FROM su_dquestion WHERE sid = ? ORDER BY qid";

  private static String GET_EACH_QUESTION_CHAT_DATA = "SELECT MAX(a.qid) as qid, COUNT(a.qid) AS COUNT, MAX(d .qname) AS qname, MAX(a.answers) AS answers,MAX(a.qid) AS qid FROM su_answer a INNER JOIN su_dquestion d ON a.qid = d .qid WHERE a.sid = ? GROUP BY a.qid order by qid";

  private static String GET_EACH_QUESTION_ANSWERS_DATA = "SELECT COUNT(id) AS count, MAX(answers) AS answers FROM su_answer WHERE (qid = ?) GROUP BY answers";

  private static String GET_A_SURVEYUSER = "SELECT * from su_userinfo where uid = ?";

  public ResultPeer(PoolServer cpool)
  {
    this.cpool = cpool;
  }

  public static IResultManager getInstance() {
    return CmsServer.getInstance().getFactory().getResultManager();
  }

  private String getAllSurveyUsersBySurveyID(int sid)
    throws ResultException
  {
    Connection conn = null;
    String userstr = "(";
    try
    {
      conn = this.cpool.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(GET_ALL_SURVEY_USERS_BY_SURVEYID);
      pstmt.setInt(1, sid);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        userstr = userstr + rs.getInt(1) + ",";
      }
      rs.close();
      pstmt.close();

      if (userstr.indexOf(",") != -1) {
        userstr = userstr.substring(0, userstr.length() - 1);
        userstr = userstr + ")";
      } else {
        userstr = "(0)";
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return userstr;
  }

  public List getAllSurveyUsersInfo(int sid) throws ResultException {
    Connection conn = null;
    List userlist = new ArrayList();

    String userstr = getAllSurveyUsersBySurveyID(sid);
    String GET_ALL_SURVEY_USERINFO = "select * from su_userinfo where uid in " + userstr + "order by uid desc";
    try
    {
      conn = this.cpool.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(GET_ALL_SURVEY_USERINFO);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        Answer answer = load(rs);
        userlist.add(answer);
      }
      rs.close();
      pstmt.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return userlist;
  }

  public List getAllSurveyUsersInfo(int sid, int start, int range) throws ResultException {
    Connection conn = null;
    List userlist = new ArrayList();

    String userstr = getAllSurveyUsersBySurveyID(sid);
    String GET_ALL_SURVEY_USERINFO = "select * from su_userinfo where uid in " + userstr + " order by uid desc";
    try
    {
      conn = this.cpool.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(GET_ALL_SURVEY_USERINFO);
      ResultSet rs = pstmt.executeQuery();

      for (int i = 0; i < start; i++) {
        rs.next();
      }
      for (int i = 0; (i < range) && (rs.next()); i++) {
        Answer answer = load(rs);
        userlist.add(answer);
      }
      rs.close();
      pstmt.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return userlist;
  }

  public int getAllSurveyUsersNumBySurveyID(int sid)
    throws ResultException
  {
    Connection conn = null;
    int unum = 0;
    try
    {
      conn = this.cpool.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(GET_ALL_SURVEY_USERSNUM_BY_SURVEYID);
      pstmt.setInt(1, sid);
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        unum = rs.getInt(1);
      }
      rs.close();
      pstmt.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return unum;
  }

  public List getAQuestionResultOfUsers(int qid, int uid)
    throws ResultException
  {
    Connection conn = null;
    List list = new ArrayList();
    try
    {
      conn = this.cpool.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(this.GET_A_QUESTION_RESULT_OF_USER);
      pstmt.setInt(1, qid);
      pstmt.setInt(2, uid);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        list.add(rs.getString(1));
      }
      rs.close();
      pstmt.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return list;
  }

  public List getAllDefineQuestionsIDBySID(int sid)
    throws ResultException
  {
    Connection conn = null;
    List list = new ArrayList();
    try
    {
      conn = this.cpool.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(GET_ALL_DEFINE_QUESTIONSID);
      pstmt.setInt(1, sid);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        int qid = rs.getInt(1);
        list.add(String.valueOf(qid));
      }
      rs.close();
      pstmt.close();
    } catch (SQLException se) {
      se.printStackTrace();
    } finally {
      if (conn != null) {
        this.cpool.freeConnection(conn);
      }
    }
    return list;
  }

  public List getEachQuestionChatData(int sid)
    throws ResultException
  {
    Connection conn = null;
    List list = new ArrayList();
    try
    {
      conn = this.cpool.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(GET_EACH_QUESTION_CHAT_DATA);
      pstmt.setInt(1, sid);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        Define define = new Define();
        define.setQid(rs.getInt("qid"));
        define.setCount(rs.getInt("count"));
        define.setQname(rs.getString("qname"));
        define.setQanswer(rs.getString("answers"));
        list.add(define);
      }
      rs.close();
      pstmt.close();
    } catch (SQLException se) {
      se.printStackTrace();
    } finally {
      if (conn != null) {
        this.cpool.freeConnection(conn);
      }
    }
    return list;
  }

  public List getEachQuestionAnswersChatData(int qid)
    throws ResultException
  {
    Connection conn = null;
    List list = new ArrayList();
    try
    {
      conn = this.cpool.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(GET_EACH_QUESTION_ANSWERS_DATA);
      pstmt.setInt(1, qid);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        Define define = new Define();
        define.setCount(rs.getInt("count"));
        define.setQanswer(rs.getString("answers"));
        list.add(define);
      }
      rs.close();
      pstmt.close();
    } catch (SQLException se) {
      se.printStackTrace();
    } finally {
      if (conn != null) {
        this.cpool.freeConnection(conn);
      }
    }
    return list;
  }

  public Answer getASurveyUser(int uid)
    throws ResultException
  {
    Connection conn = null;
    Answer answer = new Answer();
    try
    {
      conn = this.cpool.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(GET_A_SURVEYUSER);
      pstmt.setInt(1, uid);
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        answer = load(rs);
      }
      rs.close();
      pstmt.close();
    } catch (SQLException se) {
      se.printStackTrace();
    } finally {
      if (conn != null) {
        this.cpool.freeConnection(conn);
      }
    }
    return answer;
  }

  private Answer load(ResultSet rs) {
    Answer answer = new Answer();
    try
    {
      answer.setId(rs.getInt("uid"));
      answer.setUsername(rs.getString("username"));
      answer.setGender(rs.getString("gender"));
      answer.setPhone(rs.getString("phone"));
      answer.setEmail(rs.getString("email"));
      answer.setDatetime(rs.getTimestamp("datetime"));
      answer.setIp(rs.getString("ip"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return answer;
  }

  private Define loadanswer(ResultSet rs) {
    Define define = new Define();
    try
    {
      define.setQid(rs.getInt("qid"));
      define.setQname(rs.getString("qname"));
      define.setQanswer(rs.getString("answers"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return define;
  }
}