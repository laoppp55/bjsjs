<%@ page import="com.bjtoon.uia.sdk.domain.AccessTokenVo" %>
<%@ page import="com.bjtoon.uia.sdk.domain.UserInfoVo" %>
<%@ page import="com.unittest.sso" %>
<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Enumeration<String> paraNames = request.getParameterNames();
   // String grant_code = null;
	//String  state=null;

   /* while(paraNames.hasMoreElements()){
        String paraKey = paraNames.nextElement();
        String paraValue  = request.getParameter(paraKey);
        System.out.println(paraKey + "=" + paraValue);
        if (paraKey.equals("code")) {
            grant_code = paraValue;
            //break;
        }
        if(paraKey.equals("state")){
            state = paraValue;
        }

    }*/
    String grant_code = request.getParameter("code");
    String state = request.getParameter("state");
    if(grant_code!= null){ //获取grant_code
        sso sso = new sso();
        UserInfoVo userInfoVo;
        AccessTokenVo accessTokenVo = sso.getAccessToken(grant_code);
        if(accessTokenVo!=null) { //登录成功
            userInfoVo = sso.getUserInfo(accessTokenVo); //获取用户信息
            if(userInfoVo !=null ) {  //获取用户信息成功
                System.out.println("login OK!!!!");
                System.out.println("登录：grant_code="+grant_code);
                String username = userInfoVo.getUserName();
                session.setAttribute("grantCode", grant_code);
               /* Cookie CodeCookie = new Cookie("grantCode", grant_code);
                CodeCookie.setPath("/");
                response.addCookie(CodeCookie);*/
                Cookie userCookie = new Cookie("username", username);
                userCookie.setMaxAge(7200);
                userCookie.setPath("/");
                response.addCookie(userCookie);
                if(state!=null&&state.equals("1")){
                    response.sendRedirect(response.encodeRedirectURL("/zmhd/wyxx/"));
                    //return;
                }
            }

        }
    }
    response.sendRedirect(response.encodeRedirectURL("/"));
    //return;

%>
