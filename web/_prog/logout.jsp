<%@ page import="com.bjtoon.uia.sdk.domain.AccessTokenVo" %>
<%@ page import="com.bjtoon.uia.sdk.response.UiaApiResponse" %>
<%@ page import="com.unittest.sso" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="com.unittest.postUrl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%


    String grantCode = (String)session.getAttribute("grantCode");
    System.out.println("退出grantCode="+grantCode);
    Cookie uCookie = new Cookie("username",null);
    uCookie.setMaxAge(0);
    uCookie.setPath("/");
    response.addCookie(uCookie);
    if(grantCode!=null){
        sso sso = new sso();
        AccessTokenVo accessTokenVo = sso.getAccessToken(grantCode);
        if(accessTokenVo !=null) {
           // sso.getLogout(accessTokenVo);
            String access_token = accessTokenVo.getAccess_token();
            System.out.println(accessTokenVo.getAccess_token());
            session.removeAttribute("grantCode");
            String logout="https://bjt.beijing.gov.cn/renzheng/api/login/doNationalSSOLogout?access_token="+access_token;
            response.sendRedirect(response.encodeRedirectURL(logout));
        }
    }else {
        response.sendRedirect(response.encodeRedirectURL("https://bjt.beijing.gov.cn/renzheng/open/login/goUserLogin?client_id=100100000197&redirect_uri=http://www.bjsjs.gov.cn/_prog/callback.jsp&response_type=code&scope=user_info&state="));
    }


%>
