<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/stripes.tld" %>
<%
    //获取用户端的IP地址
    String user_ip = null;
    if (request.getHeader("x-forwarded-for") == null) {
        user_ip = request.getRemoteAddr();
    } else {
        user_ip = request.getHeader("x-forwarded-for");
    }

    System.out.println(user_ip);


%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>石景山区扫黑除恶专项斗争</title>
    <link rel="stylesheet"  href="css/css_2008.css"/>
    <script type="text/javascript">
        function cal(){
            document.regform.reset();
        }
        function subs(){
            var yanzheng = document.getElementById("txtVerify").value;
            var verify = document.getElementById("txtVerify").value;
            if(yanzheng ==""){
                alert("验证码不能为空");
                return false;
            }else {
               /* var objXmlc;
                if (window.ActiveXObject) {
                    objXmlc = new ActiveXObject("Microsoft.XMLHTTP");
                } else if (window.XMLHttpRequest) {
                    objXmlc = new XMLHttpRequest();
                }
                objXmlc.open("POST", "/_commons/CheceVerify.jsp?Verify=" + verify, false);
                objXmlc.send(null);
                var res = objXmlc.responseText;
                var re = res.split('-');
                var retstrs = re[0];
                if (retstrs == 0) {
                    alert("验证码错误");
                    document.all.txtVerify.value = "";
                    //shuaxin();
                    return false;
                }*/

            }
            var reportedname = document.getElementById("textfield7").value;
            if (reportedname == null || reportedname==""){
                alert("被举报人姓名不能为空");
                return false;
            }
            var county = document.getElementById("select4").value;
            if(county == -1){
                alert("请选择涉及的街道或社区");
                return false;
            }
            var reportedcontent = document.getElementById("textarea").value;
            if(reportedcontent == null || reportedcontent == ""){
                alert("请填写举报事项");
                return false;
            }else{
              if(reportedcontent.length > 3999){
                  alert("举报事项字数限制2000字");
                  return false;
              }
            }
            var unlevel = document.getElementById("textfield13").value;
            if( unlevel== null || unlevel == ""){
            }else {
                if(unlevel.length > 2 ) {
                    alert("公职人员级别只可填写数字并且不超过两位或不填");
                    return false;
                }
                if (!/^[1-9]+[0-9]*]*$/.test(unlevel)) {
                    alert("公职人员级别只可填写数字并且不超过两位或不填");
                    return false;
                }
            }

            var gzreportedcontent = document.getElementById("textarea2").value;
            if(gzreportedcontent.length > 1999){
                alert("涉案信息字数限制1000字");
                return false;
            }
            var idcardno = document.getElementById("textfield2").value;
            if(idcardno ==null || idcardno == ""){
            }else{
                return isCardNo(idcardno);
            }
            var rpidcardno = document.getElementById("textfield10").value;
            if(rpidcardno ==null || rpidcardno == ""){
            }else{
                return isCardNo(rpidcardno);
            }
            var sMobile = document.getElementById("textfield5").value;
            if(sMobile==null || sMobile=="") {
            }else{
                if (!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(sMobile))) {
                    alert("请填写正确的手机号码或者不填");
                    return false;
                }
            }
            var postcode = document.getElementById("textfield6").value;
            if(postcode ==null || postcode == "") {
            }else{
                if (!(/^[1-9][0-9]{5}$/.test(postcode))) {
                    alert("请填写正确的邮编或者不填");
                    return false;
                }
            }
            return true;
        }
        function shuaxin(){
            var verify=document.getElementById("safecode");
            verify.setAttribute("src","/_commons/drawImage.jsp?dumy="+Math.random());
        }


        function change_yzcodeimage() {
            var verify=document.getElementById("yzImageID");
            verify.setAttribute("src","image.jsp?temp=" + Math.random());
        }

        function isCardNo(card){// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
            var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
            if(reg.test(card) === false){
                alert("请输入正确的身份证号或者不填");
                return false;
            }
        }
    </script>
</head>

<body onload="cal()">
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td><img src="images/2018-1pic.jpg" width="1000" height="131" /></td>
    </tr>
</table>

<s:form name="regform" beanclass="com.bizwink.search.web.dhjbActionBean" method="post">
    <s:hidden name="reportGangdom.ipadress" value="<%=user_ip%>"/>

<table width="1000" border="0" cellspacing="0" cellpadding="0" align="center" style="background-color:#e0f0fe;">
    <tr>
        <td align="center">
            <table width="900" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="900" border="1" cellspacing="0" cellpadding="0">
                        <tr>
                            <td height="50" colspan="4" align="center" bgcolor="#cae4ff">举报人信息</td>
                        </tr>
                        <tr>
                            <td width="164" height="40" align="right">姓　　名：${actionBean.code}</td>
                            <td width="376" align="left">&nbsp;<input name="reportGangdom.jbrname" type="text" id="textfield" size="15" /></td>
                            <td width="139" align="right">性　　别：</td>
                            <td width="211" align="left">&nbsp;
                                <select name="reportGangdom.sex" id="select">
                                    <option value="1" selected>男</option>
                                    <option value="0">女</option>
                                </select></td>
                        </tr>
                        <tr>
                            <td height="40" align="right">身份证号： </td>
                            <td align="left">&nbsp;<input name="reportGangdom.idcardno" type="text" id="textfield2" size="22" /></td>
                            <td align="right">联系电话：</td>
                            <td align="left">&nbsp;<input name="reportGangdom.telphone" type="text" id="textfield5" size="15" /></td>
                        </tr>
                        <tr>
                            <td height="40" align="right">联系地址所在地区：</td>
                            <td align="left">&nbsp;<input name="reportGangdom.address" type="text" id="textfield3" size="40" /></td>
                            <td align="right">邮政编码：</td>
                            <td align="left">&nbsp;<input name="reportGangdom.postcode" type="text" id="textfield6" size="15" /></td>
                        </tr>
                        <tr>
                            <td height="40" align="right">验证码：</td>
                            <td align="left">&nbsp;<input  type="text" id="txtVerify" name="reportGangdom.yzcode"  size="15" /></td>
                            <td align="right">&nbsp;&nbsp;<img src="image.jsp" id="yzImageID" name="yzcodeimage" align="absmiddle"/>&nbsp;&nbsp;</td>
                            <td align="left"> <a href="javascript:change_yzcodeimage();">看不清换一张</a><div  id="v_mag"></div></td>
                        </tr>
                    </table></td>
                </tr>
                <tr>
                    <td><table width="900" border="1" cellspacing="0" cellpadding="0" style="margin-top:10px;">
                        <tr>
                            <td height="50" colspan="4" align="center" bgcolor="#cae4ff">被举报人信息</td>
                        </tr>
                        <tr>
                            <td width="161" height="40" align="right" class="red">姓　　名：</td>
                            <td width="378" align="left">&nbsp;<input name="reportGangdom.reportedname" type="text" id="textfield7" size="15" />
                                (必填项)</td>
                            <td width="137" align="right">昵   称：</td>
                            <td width="214" align="left">&nbsp;<input name="reportGangdom.epithet" type="text" id="textfield9" size="15" /></td>
                        </tr>
                        <tr>
                            <td height="40" align="right">联系电话住址： </td>
                            <td align="left">&nbsp;<input name="reportGangdom.rpaddress" type="text" id="textfield8" size="40" /></td>
                            <td align="right">身份证号：</td>
                            <td align="left">&nbsp;<input name="reportGangdom.rpidcardno" type="text" id="textfield10" size="15" /></td>
                        </tr>
                    </table></td>
                </tr>
                <tr>
                    <td><table width="900" border="1" cellspacing="0" cellpadding="0" style="margin-top:10px;">
                        <tr>
                            <td height="50" colspan="4" align="center" bgcolor="#cae4ff">涉及地区</td>
                        </tr>
                        <tr>
                            <td width="195" height="40" align="right">市、区、街道（必填项）：</td>
                            <td width="245">&nbsp;
                                <span class="red">北京市</span></td>
                            <s:hidden name="reportGangdom.province" value="北京市"/>
                            <td width="182">&nbsp;
                                <span class="red">石景山区</span></td>
                            <s:hidden name="reportGangdom.city" value="石景山区"/>
                            <td width="268">&nbsp;<select name="reportGangdom.county" id="select4" style="color:#999;">
                                <option value="-1">--请选择街道--</option>
                                <option value="八宝山街道"><span class="red">八宝山街道</span></option>
                                <option value="老山街道">老山街道</option>
                                <option value="八角街道">八角街道</option>
                                <option value="古城街道">古城街道</option>
                                <option value="苹果园街道">苹果园街道</option>
                                <option value="金顶街街道">金顶街街道</option>
                                <option value="广宁街道">广宁街道</option>
                                <option value="五里坨街道">五里坨街道</option>
                                <option value="鲁谷社区">鲁谷社区</option>
                            </select>
                                <span class="red">街道或社区</span></td>
                        </tr>
                    </table></td></tr>
                <tr>
                    <td><table width="900" border="1" cellspacing="0" cellpadding="0" style="margin-top:10px;">
                        <tr>
                            <td height="50" align="center" bgcolor="#cae4ff" class="red">举报事项</td>

                        </tr>
                        <tr>
                            <td align="center"><table width="98%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td align="left" valign="top">（必填项，提示“填写被举报人违法犯罪基本事实，包括案件时间、地点、人物、原因、后果、被害人、证人情况、案件处理情况等”，字数限制2000字）： </td>
                                </tr>
                                <tr>
                                    <td align="center"><label for="textarea"></label>
                                        <textarea name="reportGangdom.reportedcontent" id="textarea" cols="80" rows="10"></textarea></td>
                                </tr>
                            </table></td>

                        </tr>
                    </table></td>
                </tr>
                <tr>
                    <td><table width="900" border="1" cellspacing="0" cellpadding="0" style="margin-top:10px;">
                        <tr>
                            <td height="50" align="center" bgcolor="#cae4ff">是否有公职人员参与</td>
                        </tr>
                        <tr>
                            <td>（非必填项，“是”请填写下表公职人员涉案信息，“否”时不填写）</td>
                        </tr>
                        <tr>
                            <td><table width="898" border="1" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td height="40" colspan="6" align="center">涉及公职人员</td>
                                </tr>
                                <tr>
                                    <td width="114" height="40" align="right">姓　　名：</td>
                                    <td width="141" align="left">&nbsp;<input name="reportGangdom.gzname" type="text" id="textfield11" size="15" /></td>
                                    <td width="130" align="right">工作单位职务：</td>
                                    <td width="254" align="left">&nbsp;<input name="reportGangdom.unittitle" type="text" id="textfield12" size="30" /></td>
                                    <td width="103" align="right">级　　别：</td>
                                    <td width="142" align="left">&nbsp;<input name="reportGangdom.unlevel" type="text" id="textfield13" size="15" /></td>
                                </tr>
                                <tr>
                                    <td colspan="6" style="padding:0px 8px;">涉案信息（提示“填写被举报人违法犯罪基本事实，包括案件时间、地点、人物、原因、后果、被害人、证人情况、案件处理情况等”，字数限制1000字）：</td>
                                </tr>
                                <tr>
                                    <td colspan="6" align="center" style="padding:10px 0px;"><textarea name="reportGangdom.gzreportedcontent" id="textarea2" cols="80" rows="10"></textarea></td>
                                </tr>
                            </table></td>
                        </tr>
                    </table></td>
                </tr>
                <tr>
                    <td align="center" style="padding-top:15px;">
                        <input type="submit" name="submitMessage" id="sub" value="我要提交" class="btn_2" onclick="return subs();"/> &nbsp;&nbsp;&nbsp;
                        <input type="button"  class="btn_3" id="clear" value="我要重写" onclick="cal()" /></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
            </table>


        </td>
    </tr>
</table>
</s:form>
</body>
</html>
