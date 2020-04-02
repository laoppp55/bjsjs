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
      <td align="left" valign="middle" colspan="2">1.您是否对政务开放日活动感兴趣</td>
    </tr>
    <tr>
      <td align="left" valign="middle" colspan="2">
        <input type="radio" name="gxq" id="gxqid" value="1" />a.非常感兴趣 <br/>
        <input type="radio" name="gxq" id="gxqid" value="2" />b.感兴趣   <br/>
        <input type="radio" name="gxq" id="gxqid" value="3" />c.不感兴趣
      </td>
    </tr>
    <tr><td height="10px;" colspan="2"></td></tr>
    <tr>
      <td align="left" valign="middle" colspan="2">2.您希望在政务开放日活动中参观什么内容（可多选）</td>
    </tr>
    <tr>
      <td align="left" valign="middle" colspan="2">
        <input type="checkbox" name="viewcontent" id="viewcontentid" value="1" />a.企业开办大厅等区域营商环境工作情况绍 <br/>
        <input type="checkbox" name="viewcontent" id="viewcontentid" value="2" />b.区域老旧小区治理工作情况 <br/>
        <input type="checkbox" name="viewcontent" id="viewcontentid" value="3" />c.“接诉即办”等城市管理服务平台运行情况 <br/>
        <input type="checkbox" name="viewcontent" id="viewcontentid" value="4" />d.科技创新工作发展情况 <br/>
        <input type="checkbox" name="viewcontent" id="viewcontentid" value="5" />e.居家养老、医疗卫生、公共文化等保障改善民生工作情况 <br/>
        <input type="checkbox" name="viewcontent" id="viewcontentid" value="6" />f.其他（请详细说明）<input type="text" name="brief" value="" size="50"/>
      </td>
    </tr>
    <tr><td height="10px;" colspan="2"></td></tr>
    <tr>
      <td align="left" valign="middle" colspan="2">您希望政务开放日的活动时长？</td>
    </tr>
    <tr>
      <td align="left" valign="middle" colspan="2">
        <input type="radio" name="acttime" id="actid" value="1" />a.一小时之内 <br/>
        <input type="radio" name="acttime" id="actid" value="2" />b.1至2小时之间
      </td>
    </tr>
    <tr>
      <td align=300>请输入验证码：</td>
      <td><input  name="yzcode" type="text" id="textfield2"/><img src="/common/image.jsp" id="yzImageID" name="yzcodeimage" align="absmiddle"/> <a href="javascript:change_yzcodeimage();">看不清，换一张</a></td>
    </tr>
    <tr>
      <td align="right" valign="middle">&nbsp;</td>
      <td align="left" valign="middle"><label for="textfield2"></label>
        <input type="submit" name="button" id="button" value="提交"  style=" background-color:#e61a21; color:#FFF; border:0px; cursor:pointer; width:70px; height:24px;"  onclick="javascript:savesurvyinfo();"/></td>
    </tr>
  </table>
</form>
</body>
</html>
