function doLogin() {
        $.post("/_prog/getUserinfo.jsp",{
            },
            function(data) {
                if(data !=null) {
                    if (data.userName == "" || (typeof(data.userName) == "undefined")) {

                        $("#userInfos").html('<a href="https://bjt.beijing.gov.cn/renzheng/open/login/goUserLogin?client_id=100100000197&redirect_uri=http://www.bjsjs.gov.cn/_prog/callback.jsp&response_type=code&scope=user_info&state=">登录个人中心</a>');
                        //$("#userInfos").css({color:"red"});
                    } else {
                        $("#userInfos").html("欢迎您：<font color=\"red\">" + data.userName + "</font>&nbsp;&nbsp;<a href='#' onclick='javascript:logoff();'>退出</a>");
                    }
                }
            },
            "json"
        )

}

function logoff() {
    $.post("/_prog/logout.jsp",{},
        function(data) {
           // if (data.indexOf("nologin") > -1) {
                //var htmlcode = '<a href="/users/m/login.jsp" class="red">登录</a> | <a href="/users/m/userreg.jsp">注册</a>   <a href="/users/m/findPwd.jsp">忘记密码</a>   <a href="/users/m/personinfo.jsp">个人中心</a>';
                /*var htmlcode = '<form name="loginForm">' +
                 '<input class="txt" name="userid" type="text" /> '+
                 '<input class="txt" type="password" name="passwd" /> '+
                 '<input class="btn" type="button" name="" onclick="javascript:doLogin(prefix);" value="登录" /> '+
                 '<input class="btn" type="button" onclick="javascript:doRegister(prefix,800,1000);" value="注册" />'+
                 '</form>';
                 */
               // window.location.href="/index.shtml";
                //$("#userInfos").html(htmlcode);
           // }
        }
    )
    window.location.href="/index.shtml";
}




