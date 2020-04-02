<%@ page import="java.util.Enumeration" %>
<%@ page import="com.bjtoon.uia.sdk.domain.AccessTokenVo" %>
<%@ page import="com.unittest.sso" %>
<%@ page import="com.bjtoon.uia.sdk.domain.UserInfoVo" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.bizwink.cms.util.SecurityUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Enumeration<String> paraNames = request.getParameterNames();
    String grant_code = null;
    while(paraNames.hasMoreElements()){
        String paraKey = paraNames.nextElement();
        String paraValue  = request.getParameter(paraKey);
        System.out.println(paraKey + "=" + paraValue);
        if (paraKey.equals("code")) {
            grant_code = paraValue;
            break;
        }
    }
    session.setAttribute("grantCode",grant_code);
    sso sso = new sso();
    UserInfoVo userInfoVo = null;
    AccessTokenVo accessTokenVo = sso.getAccessToken(grant_code);
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata="{\"userName\":\"\"}";
    if(accessTokenVo!=null) {
        userInfoVo = sso.getUserInfo(accessTokenVo);
        jsondata = gson.toJson(userInfoVo);
        System.out.println(jsondata);
    }

    SecurityUtil securityUtil = new SecurityUtil();
    Cookie loginCookie = new Cookie("AuthInfo_cookie",securityUtil.encrypt(userInfoVo.getUserName() + "-" + userInfoVo.getUserId(),null));
    loginCookie.setDomain("bjsjs.gov.cn");
    loginCookie.setPath("/");
    loginCookie.setMaxAge(-1);
    response.addCookie(loginCookie);
    response.sendRedirect("/index.shtml");
%>
