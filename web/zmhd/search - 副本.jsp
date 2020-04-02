<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/stripes.tld" %>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head xmlns="">
<title>北京·石景山</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<meta content="北京石景山" name="SiteName" />  
 <meta content="www.bjsjs.gov.cn" name="SiteDomain" />  
 <meta content="1101070001" name="SiteIDCode" />  
 <meta content="政民互动" name="ColumnName" />  
 <meta content="信息收集类" name="ColumnType" />  
<link rel="stylesheet" type="text/css" href="/css/main.css" />
<link rel="stylesheet" type="text/css" href="/zwgk/css/letter.css" />
    <script type="text/javascript">
        function validateInformationForm(){
            var i = document.getElementById("searchmsg").value;
            if(i == null || i == "")
            {
                alert("请输入查询码！");
                return false;
            }
            i = document.getElementById("phone").value;
            if(i == null || i == "")
            {
                alert("请输入写信时填入的手机号！");
                return false;
            }
        }

        function isEmpty(s)
        {

            return ((s == null) || (s.value == "")||s.value=="undefine");
        }
    </script>

</head>
<body>
<div class="main">
    <!--#include virtual="/inc/menu.shtml"-->
<div style="margin-top: 20px"><img alt="" width="1000" height="180" src="/zwgk/images/wirte_banner.jpg" /></div>
<div class="con_box">
<s:form name="informationForm3" beanclass="com.bizwink.search.web.letterActionBean" method="post" onsubmit="return validateInformationForm()">
<table cellspacing="2" cellpadding="15" width="95%" align="center" border="0">
<tbody>
<tr>
<td class="deepblue" colspan="4" align="center"><font style="font-size: 24px">信件查询</font></td>
</tr>
<tr>
<td width="15%" align="right"><font style="color: #ff0000; padding-right: 5px">*</font>查询码：</td>
<td width="35%"><input id="searchmsg" class="duan" name="searchmsg" type="text" /></td>
<td width="15%" align="right"><font style="color: #ff0000; padding-right: 5px">*</font>联系电话：</td>
<td width="35%"><input id="phone" class="duan" name="phone" type="text" /></td>
</tr>
</tbody>
</table>
<div style="width: 100%; padding-bottom: 30px; text-align: center; padding-top: 30px; padding-left: 0px; padding-right: 0px">
    <%--<input id="imageField" src="/zwgk/images/submit.jpg" type="image" name="imageField" />--%>
    <input type="submit" name="searchLetter" id="sub" value="" class="button0104"/>
</div>
</form>
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