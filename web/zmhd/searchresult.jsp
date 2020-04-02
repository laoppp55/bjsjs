<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/stripes.tld" %>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head xmlns="">
<title>北京·石景山</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link rel="stylesheet" type="text/css" href="/css/main.css" />
<link rel="stylesheet" type="text/css" href="/zwgk/css/letter.css" />
 <script src="/js/jquery-1.10.2.min.js"></script>
</head>
<body>
<div class="main">
    <!--#include virtual="/inc/menu.shtml"-->
<div style="margin-top: 20px"><img alt="" width="1000" height="180" src="/zwgk/images/wirte_banner.jpg" /></div>
<div class="con_box">
<form id="form2" method="post" name="form2">
<table cellspacing="2" cellpadding="15" width="95%" align="center" border="0">
<tbody>
<tr>
<td class="deepblue" colspan="4" align="center"><font size="5">查询结果</font><br />
</td>
</tr>
<tr>
    <c:choose>
    <c:when test="${actionBean.letter==null}">
        <td class="deepblue" valign="middle" align="center">反馈</td>
        <td colspan="3">无此项内容</td>
    </c:when>
    <c:otherwise>
        <tr>
        <td class="deepblue" width="15%" align="center">标题</td>
        <td width="85%" colspan="3">${actionBean.letter.title}</td>
        </tr>
        <tr>
        <td class="deepblue" valign="middle" align="center">内容</td>
        <td colspan="3">${actionBean.letter.content}</td>
        </tr>
        <tr>
        <td class="deepblue" valign="middle" align="center">反馈</td>
        <td colspan="3">${actionBean.letter.replycontent}</td>
        </tr>
    </c:otherwise>
</c:choose>
</tbody>
</table>
</form>
</div>
<div class="con">
<div class="otherlink">
    <!--#include virtual="/inc/link1.shtml"-->
</div>
    <!--#include virtual="/inc/tail.shtml"-->
</div>
</div>
</body>
</html>