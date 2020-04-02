<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/stripes.tld" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        if(${actionBean.code} == 1){
            alert("信息提交成功，确定返回网站首页！");
            location.href="/";
        }else{
            if(${actionBean.code == -1}){
                alert("验证码错误");
            }else {
                alert("信息提交失败，请稍后再试");
            }
            location.href="/sitesearch/sjsdhjb/";
        }
    </script>
</head>
<body>

</body>
</html>
