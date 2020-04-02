<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.filter" %>
<%@ page import="com.bizwink.cms.util.Encrypt" %>
<%@ page import="com.bizwink.webapps.collection.ICollectionManager" %>
<%@ page import="com.bizwink.webapps.collection.CollectionPeer" %>
<%@ page import="com.bizwink.webapps.collection.Collection" %>
<%@ page import="com.bizwink.webapps.collection.SimpleCollection" %>
<%@ page import="com.bizwink.cms.util.JSON" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/11/14
  Time: 13:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int errcode = 0;
    request.setCharacterEncoding("utf-8");
    String yzcode = filter.excludeHTMLCode(ParamUtil.getParameter(request, "yzcode"));
    String yzcodeForSession = (String)session.getAttribute("randnum");
    String title = filter.excludeHTMLCode(ParamUtil.getParameter(request, "title"));
    String content = filter.excludeHTMLCode(ParamUtil.getParameter(request, "content"));
    int pubflag = ParamUtil.getIntParameter(request,"publicflag",0);
    int articleid = ParamUtil.getIntParameter(request,"article",0);
    String md5str = ParamUtil.getParameter(request,"md5");

    String md5_source = "&article=" + articleid + "&title=" + title + "&content=" + content + "&publicflag=" + pubflag + "&yzcode=" + yzcode;
    String yzmd5str = Encrypt.md5(md5_source.getBytes());

    System.out.println("articleid==" + articleid);
    System.out.println("pubflag==" + pubflag);
    System.out.println("title==" + title);
    System.out.println("content==" + content);
    System.out.println("yzcode==" + yzcode);
    System.out.println("yzcodeForSession==" + yzcodeForSession);
    System.out.println("md5==" + md5str + "==" + yzmd5str);

    if (yzcode.equals(yzcodeForSession)) {
        SimpleCollection collection = new SimpleCollection();
        collection.setSiteid(40);
        collection.setArticleid(articleid);
        collection.setTitle(title);
        collection.setContent(content);
        collection.setPubflag(pubflag);
        ICollectionManager collectionManager = CollectionPeer.getInstance();
        errcode = collectionManager.addCollectionInfo(collection);
        session.removeAttribute("randnum");
    }

    String jsonData = null;
    if (errcode == 0)
        jsonData =  "{\"result\":\"true\"}";
    else
        jsonData = "{\"result\":\"false\"}";
    JSON.setPrintWriter(response, jsonData,"utf-8");
%>