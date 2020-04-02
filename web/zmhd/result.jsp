<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/stripes.tld" %>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
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
    <c:choose>
        <c:when test="${actionBean.code=='1'}">
            <div class="tishi">重要提示：信息提交成功，请您牢记下方的六位查询码，它将与您所填写的手机号一起用于查询受理状态！</div>
            <div class="code">
                <p>查询码：<font size="+0">${actionBean.searchmsg}</font></p>
                <p><img alt="" width="180" height="130" src="/zwgk/images/code.jpg" /></p>
            </div>
        </c:when>
        <c:when test="${actionBean.code=='-1'}">
            <div class="tishi"></div>
            <div class="code">
                <p>信息提交成功,请勿重复提交</p>
                <p><img alt="" width="180" height="130" src="/zwgk/images/code.jpg" /></p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="tishi"></div>
            <div class="code">
                <p>信息提交失败,请稍后再试！</p>
                <p><img alt="" width="180" height="130" src="/zwgk/images/code.jpg" /></p>
            </div>
        </c:otherwise>
    </c:choose>

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