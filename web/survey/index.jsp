<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.webapps.survey.define.IDefineManager" %>
<%@ page import="com.bizwink.webapps.survey.define.DefinePeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/11/13
  Time: 23:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    IDefineManager defineMgr = DefinePeer.getInstance();
    List currentlist = new ArrayList();
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);

    int siteid = 40;
    int rows = 0;
    int totalpages = 0;
    int currentpage = 0;

    currentlist = defineMgr.getAllDefineSurvey(siteid,startrow, range);
    rows = defineMgr.getAllDefineSurveyNum(siteid);

    if (rows < range) {
        totalpages = 1;
        currentpage = 1;
    } else {
        if (rows % range == 0)
            totalpages = rows / range;
        else
            totalpages = rows / range + 1;

        currentpage = startrow / range + 1;
    }
%>
<html>
<head>
    <title>调查问题列表</title>
</head>
<body>

</body>
</html>
