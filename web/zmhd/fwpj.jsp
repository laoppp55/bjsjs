<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/stripes.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head xmlns="">
    <title>北京·石景山</title>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
    <link rel="stylesheet" type="text/css" href="/css/main.css" />
    <link rel="stylesheet" type="text/css" href="/zwgk/css/letter.css" />
    <script src="/sitesearch/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/style.js"></script>
    <script type="text/javascript" src="js/zmhd.js"></script>
    <script type="text/javascript" src="js/zmhds.js"></script>
    <script type="text/javascript">
        $('#star').raty({
        starOff: 'images/star-icon.png',
        starOn: 'images/star.png',
        score: 1

        });
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
                    <td class="deepblue" colspan="4" align="center"><font style="font-size: 24px">服务评价</font></td>
                </tr>
                <tr>
                    <td width="15%" align="right"><font style="color: #ff0000; padding-right: 5px">*</font>办事事项：</td>
                    <td colspan="3"><input id="title" class="biaoti" name="letter.title" type="text" /></td>
                </tr>
                <tr>
                    <td valign="middle" align="right"><font style="color: #ff0000; padding-right: 5px">*</font>评价内容：</td>
                    <td colspan="3"><textarea id="content" class="neirong" rows="8" cols="100" name="letter.content"></textarea></td>
                </tr>
                <tr>
                    <td align="right"><font style="color: #ff0000; padding-right: 5px">*</font>您的姓名：</td>
                    <td width="35%"><input id="linkman" class="duan" name="letter.linkman" type="text" /></td>
                    <td width="15%" align="right"><font style="color: #ff0000; padding-right: 5px">*</font>您的电话：</td>
                    <td width="35%"><input id="phone" class="duan" name="letter.phone" type="text" /></td>
                </tr>
                <tr>
                    <td align="right"><font style="color: #ff0000; padding-right: 5px">*</font>服务评分：</td>
                    <td colspan="3">
                        <div id="star" data-score="1" style="width:100px;cursor: pointer">
                            <img src="images/star.png" alt="1" title="bad"/>
                            <img src="images/star.png" alt="2" title="poor"/>
                            <img src="images/star.png" alt="3" title="regula"/>
                            <img src="images/star.png" alt="4" title="good"/>
                            <img src="images/star.png" alt="5" title="gorgeous"/>
                            <input type="hidden" name ="score" value="5"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td align="right"><font style="color: #ff0000; padding-right: 5px">*</font>验证码：</td>
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