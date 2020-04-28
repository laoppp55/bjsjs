<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.webapps.survey.define.DefinePeer" %>
<%@ page import="com.bizwink.webapps.survey.define.IDefineManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.survey.define.Define" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.webapps.survey.define.DefineException" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=utf-8" %>

<%
    int sid = ParamUtil.getIntParameter(request, "sid", -1);

    IDefineManager defineMgr = DefinePeer.getInstance();
    List questionlist = new ArrayList();
    List answerlist = new ArrayList();

    if (sid != -1) {
        try {
            questionlist = defineMgr.getAllDefineQuestionsBySID(sid);
        } catch (DefineException e) {
            e.printStackTrace();
        }
    }

    Define defineSurvey = defineMgr.getADefineSurvey(sid);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat contentsdf = new SimpleDateFormat("yyyy-MM-dd");
    Timestamp now = new Timestamp(System.currentTimeMillis());
    Calendar s_cal = Calendar.getInstance();
    s_cal.set(Calendar.YEAR,2020);
    s_cal.set(Calendar.MONTH,3);
    s_cal.set(Calendar.DAY_OF_MONTH,27);
    Timestamp startdate = new Timestamp(s_cal.getTimeInMillis());

    Calendar e_cal = Calendar.getInstance();
    e_cal.set(Calendar.YEAR,2020);
    e_cal.set(Calendar.MONTH,4);
    e_cal.set(Calendar.DAY_OF_MONTH,27);
    Timestamp enddate = new Timestamp(e_cal.getTimeInMillis());
    boolean submit_flag = false;
    if (sid==301) {
        if (now.before(enddate) && now.after(startdate)) submit_flag = true;
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
    <link href="/css/css_2019.css" rel="stylesheet" type="text/css" />
    <!--link href="/css/news_zmhd_2020.css" rel="stylesheet" type="text/css" /-->
    <link rel=stylesheet type=text/css href=style/global.css>
    <script type="text/javascript" src="/js/style.js"></script>
    <script src="/js/jquery-1.12.4.js" type="text/javascript"></script>
    <script type="text/javascript">
        function check() {
            <%
              for(int i=0;i<questionlist.size();i++){
                Define define = (Define)questionlist.get(i);
                int qmust = define.getQmust();
                int qid = define.getQid();

                if(qmust == 2){
            %>
            var mustflag = false;
            for (var i = 0; i < answerForm.answer<%=qid%>.length; i++) {
                if (answerForm.answer<%=qid%>[i].checked) {
                    mustflag = true;
                    break;
                }
            }
            if (!mustflag) {
                alert("请选择问题 <%=i+1%> 的答案！");
                return false;
            }
            <%
                }
              }
            %>

            var yzcode = answerForm.yzcode.value;

            if (yzcode == null || yzcode =="" ||typeof yzcode == "undefined") {
                alert("请输入验证码，验证码不能为空");
                return false;
            }

            answerForm.submit();
            return true;
        }

        function checkNum(listsize, nother, qid, which, qtype) {
            var otherBtnName = "answerForm.other" + qid;
            var o = eval(otherBtnName);
            if (nother == 1) {
                if (listsize == which) {
                    if (qtype == 1) {
                        o.disabled = 0;
                    } else {
                        if (eval("answerForm.answer" + qid + "[" + listsize + "]").checked) {
                            o.disabled = 0;
                        } else {
                            o.disabled = 1;
                        }
                    }
                } else {
                    o.disabled = 1;
                }
            }
        }

        function viewResult() {
            window.open("viewResult.jsp?sid=<%=sid%>");
        }

        function change_yzcodeimage() {
            $("#yzImageID").attr("src","/survey/image.jsp?temp=" + Math.random());
        }
    </script>
</head>

<body bgcolor="#f5f6f8">
<div><jsp:include page="/inc/head2020.shtml"></jsp:include></div>

<div class="main">
    <div class="location">当前位置：<A HREF=/index.shtml>首页</A> > <A HREF=/zmhd/index.shtml>政民互动</A> > <A HREF=/zmhd/dczj/index.shtml>调查征集</A>


    </div>

    <div class="article">
        <div class="title"><%=defineSurvey.getSurveyname()%></div>

        <div class="time">发布日期：<%=contentsdf.format(defineSurvey.getStartdatetime())%> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;来源：区经济和信息化局</div>

        <div class="txt">
            <p style="text-align: center">开始时间：<%=contentsdf.format(defineSurvey.getStartdatetime())%>　　结束时间：<%=contentsdf.format(defineSurvey.getEnddatetime())%></p>
            <!--以为为头部公共部分-->
            <div style="margin-top: 50px;margin-left: 300px;">
                <form action="answer.jsp" method="post" name="answerForm">
                    <input type="hidden" name="sid" value="<%=sid%>">
                    <table width=80% border=0 cellpadding=0 cellspacing=0 align='center' width=157 height=217>
                        <tr>
                            <td>
                                <table border="0" width="100%" cellspacing="1" cellpadding=3>
                                    <%
                                        if (questionlist.size() > 0) {
                                            for (int i = 0; i < questionlist.size(); i++) {
                                                Define qdefine = (Define) questionlist.get(i);
                                                int qid = qdefine.getQid();
                                                String qname = qdefine.getQname();
                                                int qtype = qdefine.getQtype();
                                                int nother = qdefine.getNother();
                                                int atype = qdefine.getAtype();
                                    %>
                                    <tr>
                                        <td width="5%" align=left><%=StringUtil.iso2gb(qname)%>
                                        </td>
                                    </tr>
                                    <%
                                        try {
                                            answerlist = defineMgr.getAllDefineAnswersByQID(qid);
                                        } catch (DefineException e) {
                                            e.printStackTrace();
                                        }
                                    %>
                                    <tr height=35>
                                        <td width="5%" align=left>
                                            <table border=0>
                                                <%
                                                    if (answerlist != null) {
                                                        for (int j = 0; j < answerlist.size(); j++) {
                                                            Define adefine = (Define) answerlist.get(j);
                                                            String qanswer = adefine.getQanswer();
                                                            String picurl = adefine.getPicurl();

                                                            if (atype != 1) {
                                                                qanswer = picurl;
                                                            }
                                                %>
                                                <tr>
                                                    <td>
                                                        <%if (qtype == 1) {%>
                                                        <input type="radio" name="answer<%=qid%>" value="<%=StringUtil.iso2gb(qanswer)%>"
                                                               onClick="checkNum(<%=answerlist.size()-1%>,<%=nother%>,<%=qid%>,<%=j%>,<%=qtype%>);">
                                                        <%if (atype == 1) {%><%=StringUtil.iso2gb(qanswer)%><%} else {%><a href="../answerspic/<%=picurl%>"
                                                                                                                           target=_blank><img
                                                            src="../answerspic/<%=picurl%>"
                                                            alt="" width=160 border=0></a><%}%>&nbsp;&nbsp;
                                                        <%} else {%>
                                                        <input type="checkbox" name="answer<%=qid%>" value="<%=StringUtil.iso2gb(qanswer)%>"
                                                               onClick="checkNum(<%=answerlist.size()-1%>,<%=nother%>,<%=qid%>,<%=j%>,<%=qtype%>);">
                                                        <%if (atype == 1) {%><%=StringUtil.iso2gb(qanswer)%><%} else {%><a href="../answerspic/<%=picurl%>"
                                                                                                                           target=_blank><img
                                                            src="../answerspic/<%=picurl%>"
                                                            alt="" width=160 border=0></a><%}%>&nbsp;&nbsp;
                                                        <%
                                                                }
                                                            }
                                                            if (nother == 1) {
                                                        %>
                                                        <input type="text" name="other<%=qid%>">
                                                        <script type="text/javascript">
                                                            answerForm.other<%=qid%>.disabled = 1;
                                                        </script>
                                                        <%
                                                            }
                                                        %>
                                                    </td>
                                                </tr>
                                                <%
                                                    }
                                                %>
                                            </table>
                                        </td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                    <!--tr bgcolor="#ffffff">
                                      <td>用户姓名：<input type="text" name="username">&nbsp;&nbsp;
                                        性别：<input type="radio" name="gender" value="男" checked>男&nbsp;<input type="radio" name="gender" value="女">女
                                      </td>
                                    </tr>
                                    <tr bgcolor="#ffffff">
                                      <td>联系电话：<input type="text" name="phone"></td>
                                    </tr>
                                    <tr bgcolor="#ffffff">
                                      <td>电子邮件：<input type="text" name="email"></td>
                                    </tr-->
                                    <% if (submit_flag) {%>
                                    <tr><td align=300>请输入验证码：<input  name="yzcode" type="text" /><img src="image.jsp" id="yzImageID" name="yzcodeimage" align="absmiddle"/> <a href="javascript:change_yzcodeimage();">看不清，换一张</a></td></tr>
                                    <tr>
                                        <td align=center height=50><input type="button" name="subbutton" value="提   交" onclick="return check();">&nbsp;
                                            <input type="button" name="surveybut" value="查看结果" onclick="viewResult();">&nbsp;
                                        </td>
                                    </tr>
                                    <%}%>
                                    <%
                                        } else {
                                            out.println("<script type=\"text/javascript\">");
                                            out.println("alert(\"调查定义不完整，请完整定义后预览调查！\");");
                                            out.println("window.close();");
                                            out.println("</script>");
                                        }
                                    %>
                                </table>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="clert">&nbsp;</div>
            <!--底部开始-->
            <div class="bottom"><jsp:include page="/inc/tail2020.shtml"></jsp:include></div>
        </div>
    </div>
</div>
</body>
</html>