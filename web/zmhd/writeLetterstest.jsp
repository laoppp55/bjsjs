<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/stripes.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head xmlns="">
<title>北京·石景山</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link rel="stylesheet" type="text/css" href="/css/main.css" />
<link rel="stylesheet" type="text/css" href="/zwgk/css/letter.css" />
<script src="/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/style.js"></script>
<script type="text/javascript" src="js/zmhd.js"></script>
<script type="text/javascript" src="js/zmhds.js"></script>
    <script type="text/javascript">
        $(function(){
            doLoginLetter();
            //getDepartment();
            getCategory();
        });
    </script>
</head>
<body>
<div class="main">
<%--<!--#include virtual="/inc/menu.shtml"-->--%>
    <div id="userInfos" class="login_box"><a href="https://bjt.beijing.gov.cn/renzheng/open/login/goUserLogin&#63;client_id=100100000197&amp;redirect_uri=http://www.bjsjs.gov.cn/_prog/callback.jsp&amp;response_type=code&amp;scope=user_info&amp;state=1">登录个人中心</a></div>
    <div class="banner_flash_bg"><object height="236" width="1000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000">
        <param name="movie" value="/images/banner.swf" />
        <param name="quality" value="high" /><embed height="236" width="1000" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" quality="high" src="/images/banner.swf"></embed></object></div>
    <!--div class="banner"><span class="beijing"><a target="_blank" href="http://www.beijing.gov.cn"><首都之窗></a></span></div-->
    <div class="menu">
        <ul>
            <li class="mlist m1"><a href="/">网站首页</a></li>
            <li class="mlist m2"><a href="/zwgk/">政务公开</a></li>
            <li class="mlist m3"><a href="/wsbs/">网上办事</a></li>
            <li class="mlist m4"><a href="/zmhd/">政民互动</a></li>
            <li class="mlist m5"><a target="_blank" href="/yshj/">优化营商环境</a></li>
            <li class="mlist m6"><a href="/bxsh/jiedao/home.shtml">百姓生活</a></li>
            <li class="mlist m7"><a href="/zxds/">在线电视</a></li>
        </ul>
    </div>
    <div class="date_search">
        <div class="date"><script type="text/javascript" src="/js/date.js"></script></div>
        <div class="wether"><!--iframe border="0" name="ReplyFrame" align="top" marginwidth="0" marginheight="0" src="http://m.weather.com.cn/m/pn7/weather.htm" frameborder="0" width="180" scrolling="no" height="20" allowtransparency="allowtransparency"></iframe--></div>
        <div class="search">
            <form id="form1" method="post" action="/sitesearch/sitesearch" name="form1">
                <label><input class="keyword" onclick="if(this.value=='请输入搜索关键字')this.value=''" onblur="if (value ==''){value='请输入搜索关键字'}" name="searchWord" value="请输入搜索关键字" type="text" /><input id="pagesize" type="hidden" value="50" /> </label><label><input class="anniu" src="/images/search.gif" type="image" name="Submit" value="提交" /> </label>
            </form>
        </div>
        <div class="wzabox"><a href="http://www.bjsjs.gov.cn/wca.html"><img alt="" src="/images/wza20181130.png" /></a></div>
    </div>
<div style="margin-top: 20px"><img alt="" width="1000" height="180" src="/zwgk/images/wirte_banner.jpg" /></div>
<div class="con_box">
    <s:form name="informationForm3" beanclass="com.bizwink.search.web.letterActionBean" method="post" onsubmit="return validateInformationForm()">
        <input type="hidden"  id="userid" name="letter.userid"/>
<table cellspacing="2" cellpadding="15" width="95%" align="center" border="0">
<tbody>
<tr>
<td class="deepblue" colspan="4" align="center"><font style="font-size: 24px">我要写信</font></td>
</tr>
<tr>
<td width="15%" align="right"><font style="color: #ff0000; padding-right: 5px">*</font>标题：</td>
<td colspan="3"><input id="title" class="biaoti" name="letter.title" type="text" /></td>
</tr>
<tr>
<td valign="middle" align="right"><font style="color: #ff0000; padding-right: 5px">*</font>内容：</td>
<td colspan="3"><textarea id="content" class="neirong" rows="8" cols="100" name="letter.content"></textarea></td>
</tr>
<tr>
<td align="right"><font style="color: #ff0000; padding-right: 5px">*</font>联系人：</td>
<td width="35%"><input id="linkman" class="duan" name="letter.linkman" type="text" /></td>
<td width="15%" align="right"><font style="color: #ff0000; padding-right: 5px">*</font>联系电话：</td>
<td width="35%"><input id="phone" class="duan" name="letter.phone" type="text" /></td>
</tr>
<tr>
<td align="right">信件类型：</td>
<td colspan="3"><select id="categoryCode" style="width: 240px; margin: 0px" name="letter.categorycode">
<option value="0" selected="selected">--------请选择--------</option>
</select></td>
</tr>
<tr>
<td align="right">验证码：</td>
<td><input id="yzcode" class="duan" name="letter.yzcode" type="text" /></td>
<td align="center"><img id="yzImageID" name="yzcodeimage" alt="" align="absMiddle" src="image.jsp" /></td>
<td><a href="javascript:change_yzcodeimage();">看不清换一张</a></td>
</tr>
</tbody>
</table>
<div style="width: 100%; padding-bottom: 30px; text-align: center; padding-top: 30px; padding-left: 0px; padding-right: 0px">
    <!--input id="imageField" src="/zwgk/images/submit.jpg" type="image" name="submitMessage" onclick="return submit2();"/-->
    <input type="submit" name="submitMessage" id="sub" value="" class="button0104" onclick="return submit2();"/>
</div>
</s:form>
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