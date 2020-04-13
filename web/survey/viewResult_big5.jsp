<%@ page import="java.text.DecimalFormat" %>
<%@ page import="com.bizwink.webapps.survey.answer.IAnswerManager" %>
<%@ page import="com.bizwink.webapps.survey.answer.AnswerPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.webapps.survey.define.DefinePeer" %>
<%@ page import="com.bizwink.webapps.survey.define.Define" %>
<%@ page import="com.bizwink.webapps.survey.define.IDefineManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.survey.define.DefineException" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.webapps.survey.answer.AnswerException" %>
<%@ page contentType="text/html;charset=utf-8" %>

<%
  int sid = ParamUtil.getIntParameter(request, "sid", -1);

  IDefineManager defineMgr = DefinePeer.getInstance();
  IAnswerManager answerMgr = AnswerPeer.getInstance();
  List questionlist = new ArrayList();
  List answerlist = new ArrayList();
  double percent;
  double imgwidth;
  String surveyname = "";
  DecimalFormat df = new DecimalFormat("0.00");

  try {
    questionlist = defineMgr.getAllDefineQuestionsBySID(sid);
    Define define = defineMgr.getADefineSurvey(sid);
    surveyname = define.getSurveyname();
  } catch (Exception e) {
    e.printStackTrace();
  }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>北京石景山</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta content="width=1200" name="viewport">
  <meta content="北京石景山" name="SiteName" />
  <meta content="www.bjsjs.gov.cn" name="SiteDomain" />
  <meta content="1101070001" name="SiteIDCode" />
  <meta content="要闻动态" name="ColumnName" />
  <meta content="要闻动态首页" name="ColumnType" />
  <link href="/css/common_2020.css" rel="stylesheet" type="text/css" />
  <link href="/css/news_zmhd_2020.css" rel="stylesheet" type="text/css" />
  <link rel=stylesheet type=text/css href=style/global.css>
  <script type="text/javascript" src="/js/style.js"></script>
  <script src="/js/jquery-1.12.4.js" type="text/javascript"></script>
  <style type="text/css">
    <!--
    body {
      margin-top: 0px;
      margin-bottom: 0px;
    }

    -->
  </style>
  <link href="images/css.css" rel="stylesheet" type="text/css"/>
</head>

<body bgcolor="#f5f6f8">
<div><jsp:include page="/inc/head2020_big5.shtml"></jsp:include></div>
<!--以为为头部公共部分-->
<div style="margin-top: 50px;margin-left: 300px;">
<table width="900" border="0" cellpadding="0" cellspacing="0" class="bian">
<%
  if ((questionlist != null) && (questionlist.size() > 0)) {
    int totalcount = 0;
    try {
      totalcount = answerMgr.getSurveyUsersCount(sid);
    } catch (AnswerException e) {
      e.printStackTrace();
    }
%>
<tr>
<td height="360" valign="top">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
  <td>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="50" height="40" align="center"><img src="images/qian_02.jpg" width="30" height="30"/></td>
        <td width="700" class="black12c"><%=StringUtil.iso2gb(surveyname)%>调查问卷</td>
        <td align="center"><!--span class="black12">共</span><span class="red12"><%=totalcount%></span><span
                class="black12">人参加调查</span-->
        </td>
      </tr>

    </table>
  </td>
</tr>
<%
  for (int i = 0; i < questionlist.size(); i++) {
    Define qdefine = (Define) questionlist.get(i);
    int qid = qdefine.getQid();
    String qname = qdefine.getQname();
    int atype = qdefine.getAtype();
%>
<tr>
  <td>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" bgcolor="#898898"></td>
      </tr>
      <tr>
        <td height="30" bgcolor="#F6F5F0" class="black12">&nbsp;&nbsp;<%=i + 1%>.<%=StringUtil.iso2gb(qname)%>
        </td>
      </tr>
      <tr>
        <td height="1" bgcolor="#898898"></td>
      </tr>
      <tr>
        <td height="30">
          <table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="50" align="center" class="black12">编号</td>
              <td width="1" bgcolor="#898898"></td>
              <td width="300" align="center" class="black12">答案选项</td>
              <td width="1" bgcolor="#898898"></td>
              <td width="300" align="center" class="black12">比例</td>
              <td width="1" bgcolor="#898898"></td>
              <td width="244" align="center" class="black12">实际票数</td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td height="1" bgcolor="#898898"></td>
      </tr>
      <tr>
        <td>
          <%
            try {
              answerlist = defineMgr.getAllDefineAnswersByQID(qid);
            } catch (DefineException e) {
              e.printStackTrace();
            }
            try {
              totalcount = answerMgr.getASurveyUsersCount(qid);
              if (totalcount == 0) totalcount = 1;
              for (int j = 0; j < answerlist.size(); j++) {
                Define adefine = (Define) answerlist.get(j);
                String qanswer = adefine.getQanswer();
                String picurl = adefine.getPicurl();
                if (atype != 1) qanswer = picurl;
                int count = answerMgr.getAQuestionUsersCount(StringUtil.iso2gb(qanswer),adefine.getQid());
                percent = Double.parseDouble(df.format(count * 100.0 / totalcount));
                imgwidth = Double.parseDouble(df.format(count * 80.0 / totalcount));
          %>
          <table width="100%" height="80" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="50" align="center" class="black12"><%=j + 1%>
              </td>
              <td width="1" bgcolor="#898898"></td>
              <td width="300" align="center" class="black12">
                <%if (atype == 1) {%>
                <font color=#0262cd><%=StringUtil.iso2gb(qanswer)%>
                </font>
                <%} else {%>
                <a href="../answerspic/<%=picurl%>" target=_blank><img src="../answerspic/<%=picurl%>" alt="" width=60
                                                                       height=60 border=0></a>
                <%}%>
              </td>
              <td width="1" bgcolor="#898898"></td>
              <td width="300" align="center" class="red12">
                <table border=0>
                  <tr>
                    <td align=left width=40 class="black12"><font color=#0262cd><%=percent%>%</font></td>
                    <td align=left width=60><img src="images/line.gif" width=<%=imgwidth%>
                            height=10 alt="所占比例"></td>
                  </tr>
                </table>
              </td>
              <td width="1" bgcolor="#898898"></td>
              <td width="244" align="center" class="black12"><%=count%>
              </td>
            </tr>
            <tr>
              <td height="1" align="center" bgcolor="#898898" class="black12"></td>
              <td height="1" bgcolor="#898898"></td>
              <td height="1" align="center" bgcolor="#898898" class="black12"></td>
              <td height="1" bgcolor="#898898"></td>
              <td height="1" align="center" bgcolor="#898898" class="red12"></td>
              <td height="1" bgcolor="#898898"></td>
              <td height="1" align="center" bgcolor="#898898" class="black12"></td>
            </tr>
          </table>
          <%
              }
            } catch (AnswerException e) {
              e.printStackTrace();
            }
          %>
        </td>
      </tr>
      <tr>
        <td height="30"></td>
      </tr>
    </table>
    <%}%>
  </td>
</tr>
<tr>
  <td height="50" bgcolor="#F6F5F0">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="200"></td>
        <td width="180" class="red12"><a href="viewResult.jsp?sid=<%=sid%>">请您刷新以获得最新的统计结果！</a></td>
        <td></td>
        <td width="50" class="black12" align=center></td>
        <td></td>
        <td width="50" class="black12" align=center></td>
        <td></td>
        <td width="100" class="black12"></td>
        <td width="200"></td>
      </tr>
    </table>
  </td>
</tr>

</table>
</td>
</tr>
<%
  } else {
    out.println("<script type=\"text/javascript\">");
    out.println("alert(\"调查定义不完整，请完整定义后查看调查结果！\");");
    out.println("window.close();");
    out.println("</script>");
  }
%>
</table>
</div>
<div class="clert">&nbsp;</div>
<!--底部开始-->
<div class="bottom"><jsp:include page="/inc/tail2020_big5.shtml"></jsp:include></div>
</body>
</html>



