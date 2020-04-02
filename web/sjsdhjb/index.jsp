<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>石景山区扫黑除恶专项斗争</title>
    <link rel="stylesheet"  href="css/css_2008.css"/>
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript">
        var intervalProcess = null, i = 15;
        $(document).ready(function(){
            $("input[type='button'][value='我要举报']").bind('click',function(){
                window.location="sjsdhjb.jsp";
            });

            acceptLicense();
        });
        function waiteTime() {
            i--;
            $("input[type='button'][value='我要举报']").each(function(o) {
                $(this).val($(this).attr("value")+"("+i+")");
                if(i <= 0) {
                    $(this).val($(this).attr("value"));
                    $(this).attr("disabled", false);
                }
            });
            if(i <= 0) {
                clearInterval(intervalProcess);
            }
        }

        function acceptLicense(){
            var acceptCheck = document.getElementById("checkbox");
            if(acceptCheck.checked == true){
                document.getElementById("button").disabled="";
                //document.getElementById("nm").disabled="";
                //document.getElementById("sm").className="btn_2";
                //document.getElementById("nm").className="btn_3";
            } else {
                document.getElementById("button").disabled="disabled";
                //document.getElementById("nm").disabled="disabled";
                //document.getElementById("sm").className="btn_grey";
                //document.getElementById("nm").className="btn_grey";
            }
        }
    </script>

</head>

<body>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td><img src="images/2018-1pic.jpg" width="1000" height="131" /></td>
    </tr>
</table>
<table width="1000" border="0" cellspacing="0" cellpadding="0" align="center" style="background-color:#e0f0fe;">
    <tr>
        <td align="center">
            <table width="900" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td height="50" align="center" bgcolor="#cae4ff">举报须知</td>
                </tr>
                <tr>
                    <td align="left">一、本平台受理对涉黑涉恶违法犯罪行为的检举控告。<br />
                        二、举报人应当如实反映情况。对于借举报捏造事实、诬告陷害的，依照有关规定严肃处理；涉嫌违法犯罪的，依法处理。<br />
                        三、提倡实名举报（请填写真实姓名、身份信息以及准确联系方式），并依法为举报人保密。<br />
                        四、为保障举报人的合法权益，限制恶意重复举报和垃圾信息，提高网上举报运行效率，举报人每天可提交3件举报。<br />
                        五、反映其他问题，请到有关部门举报网站举报。</td>
                </tr>
                <tr>
                    <td height="50" align="center" bgcolor="#cae4ff">相关规定</td>
                </tr>
                <tr>
                    <td align="left"><p>一、黑社会性质的组织应当同时具备以下特征：<br />
                        （一）形成较稳定的犯罪组织，人数较多，有明确的组织者、领导者，骨干成员基本固定；<br />
                        （二）有组织地通过违法犯罪活动或者其他手段获取经济利益，具有一定的经济实力，以支持该组织的活动；<br />
                        （三）以暴力、威胁或者其他手段，有组织地多次进行违法犯罪活动，为非作恶，欺压、残害群众；<br />
                        （四）通过实施违法犯罪活动，或者利用国家工作人员的包庇或者纵容，称霸一方，在一定区域或者行业内，形成非法控制或者重大影响，严重破坏经济、社会生活秩序。<br />
                        二、恶势力是指经常纠集在一起，以暴力、威胁或者其他手段，在一定区域或者行业内多次实施违法犯罪活动，为非作恶，欺压百姓，扰乱经济、社会生活秩序，造成较为恶劣的社会影响，但尚未形成黑社会性质组织的违法犯罪团伙。恶势力一般为三人以上，纠集者相对固定，违法犯罪活动主要为强迫交易、故意伤害、非法拘禁、敲诈勒索、故意毁坏财物、聚众斗殴、寻衅滋事等，同时还可能伴随实施开设赌场、组织妇女卖淫、强迫妇女卖淫、贩卖毒品、运输毒品、制造毒品、抢劫、抢夺、聚众扰乱社会秩序、聚众扰乱公共场所秩序、交通秩序以及聚众&ldquo;打砸抢&rdquo;等。</p>
                        <p>
                            <input type="checkbox" name="checkbox" id="checkbox" onclick="acceptLicense()" style="width:25px;height:25px;float:left"/>

                            <label for="checkbox" style="float:left">我已阅读以上条款</label>
                            </p></td>
                </tr>
                <tr>
                    <td height="100" align="center"><input type="button" name="button" id="button" value="我要举报" class="btn_1" /></td>
                </tr>
                <tr>
                    <td align="center">
                    </td>
                </tr>
            </table>

        </td>
    </tr>
</table>

</body>
</html>
