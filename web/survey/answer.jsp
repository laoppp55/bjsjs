<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.survey.answer.IAnswerManager" %>
<%@ page import="com.bizwink.webapps.survey.answer.AnswerException" %>
<%@ page import="com.bizwink.webapps.survey.answer.AnswerPeer" %>
<%@ page import="com.bizwink.webapps.survey.define.Define" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.webapps.survey.define.DefinePeer" %>
<%@ page import="com.bizwink.webapps.survey.define.DefineException" %>
<%@ page import="com.bizwink.webapps.survey.define.IDefineManager" %>
<%@ page import="com.bizwink.cms.util.filter" %>
<%@ page contentType="text/html;charset=utf-8" %>

<%
    request.setCharacterEncoding("utf-8");
    int sid = ParamUtil.getIntParameter(request, "sid", -1);
    int errcode = 0;
    Cookie[] cookies = request.getCookies();
    boolean existflag = false;
    for (int k = 0; k < cookies.length; k++) {
        Cookie c = cookies[k];
        String name = c.getName();

        if (name.equals("Survey_Bizwink_" + sid)) {
            existflag = true;
        }
    }

    if (existflag) {
        out.println("<script language=javascript>");
        out.println("alert(\"您已经投过票了！谢谢！\");");
        out.println("window.location='viewResult.jsp?sid=" + sid + "';");
        out.println("</script>");
    } else {
        IAnswerManager answerMgr = AnswerPeer.getInstance();
        IDefineManager defineMgr = DefinePeer.getInstance();
        List questionlist = new ArrayList();
        try {
            questionlist = defineMgr.getAllDefineQuestionsBySID(sid);
        } catch (DefineException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < questionlist.size(); i++) {
            Define define = (Define) questionlist.get(i);
            int qid = define.getQid();
            int nother = define.getNother();

            String[] answers = request.getParameterValues("answer" + qid);
            String other = "";
            if (nother == 1) {
                other = ParamUtil.getParameter(request, "other" + qid);
            }
            try {
                String yzcode = filter.excludeHTMLCode(ParamUtil.getParameter(request, "yzcode"));
                String yzcodeForSession = (String)session.getAttribute("randnum");
                if (yzcode != null && yzcode !="") {
                    if (yzcode.equals(yzcodeForSession)) {
                        answerMgr.createUserAnswers(sid, qid, 0, answers, nother, other);
                    }else
                        errcode = -1;
                } else {
                    errcode = -2;
                }
            } catch (AnswerException e) {
                e.printStackTrace();
            }
        }

        if (errcode == 0) {
            //写入COOKIE
            session.removeAttribute("randnum");
            Cookie cookie = new Cookie("Survey_Bizwink_" + sid, String.valueOf(sid));
            cookie.setMaxAge(60 * 60 * 24 * 2);
            response.addCookie(cookie);

            out.println("<script type=\"text/javascript\">");
            out.println("alert(\"投票成功，谢谢您的参与！\");");
            //out.println("window.close();");
            //out.println("window.history.go(-1);");
            out.println("window.location='viewResult.jsp?sid=" + sid + "';");
            out.println("</script>");
        } else {
            out.println("<script type=\"text/javascript\">");
            out.println("alert(\"验证码输入为空或者输入错误，请重新投票！\");");
            out.println("</script>");
        }
    }
%>