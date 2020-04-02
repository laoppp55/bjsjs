<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>意见征集</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <script src="/js/jquery-1.12.4.js" type="text/javascript"></script>
  <script src="/js/md5-min.js" type="text/javascript"></script>
  <script src="/js/collection.js" type="text/javascript"></script>
</head>

<body>
<form name="collectionForm">
  <input type="hidden" name="articleid" value="10012" />
  <table width="100%" border="0" cellpadding="0" cellspacing="10">
    <tr>
      <td height="30" colspan="2" bgcolor="#f4f3f3">我有话说</td>
    </tr>
    <tr>
      <td width="21%" align="right" valign="middle">建议标题：</td>
      <td width="79%" align="left" valign="middle">
        <input name="title" type="text" id="titleid" size="35" /></td>
    </tr>
    <tr>
      <td align="right" valign="middle">建议内容：</td>
      <td align="left" valign="middle">
        <textarea name="content" id="contentid" cols="45" rows="5"></textarea></td>
    </tr>
    <tr>
      <td align="right" valign="middle">是否公开：</td>
      <td align="left" valign="middle">是
        <input type="radio" name="pubic" id="publicid" value="1" />
        否
        <input type="radio" name="public" id="publicid" value="0" /></td>
    </tr>
    <tr>
      <td align=300>请输入验证码：</td>
      <td><input  name="yzcode" type="text" id="textfield2"/><img src="/common/image.jsp" id="yzImageID" name="yzcodeimage" align="absmiddle"/> <a href="javascript:change_yzcodeimage();">看不清，换一张</a></td>
    </tr>
    <tr>
      <td align="right" valign="middle">&nbsp;</td>
      <td align="left" valign="middle"><label for="textfield2"></label>
        <input type="submit" name="button" id="button" value="提交"  style=" background-color:#e61a21; color:#FFF; border:0px; cursor:pointer; width:70px; height:24px;"  onclick="javascript:saveinfo();"/></td>
    </tr>
  </table>
</form>
</body>
</html>
