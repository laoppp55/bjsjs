﻿<%@ page import="com.bjtoon.uia.sdk.domain.AccessTokenVo" %>
<%@ page import="com.unittest.sso" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    sso sso = new sso();
    String grantCode = (String)session.getAttribute("grantCode");
    AccessTokenVo accessTokenVo = sso.getAccessToken(grantCode);
    sso.getLogout(accessTokenVo);
    session.removeAttribute("grantCode");
%>
