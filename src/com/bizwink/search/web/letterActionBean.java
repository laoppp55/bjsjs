package com.bizwink.search.web;

import com.bizwink.search.domain.Letter;
import com.bizwink.search.service.LetterService;
import com.bizwink.search.util.filter;
import com.google.gson.Gson;
import com.xdt.demo.ResponseModel;
import com.xdt.demo.XDTAPI;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kang on 2018/12/17.
 */
public class letterActionBean  extends AbstractActionBean  {
    @SpringBean
    LetterService letterService;
    Letter letter;
    int code;
    String searchmsg;
    String phone;
    int page;
    int pageSize;
    int selectedflag=1; // 0不公开 1公开
    int statusflag=0;   //0未处理  1已处理
    int searchId;
    String userid; //用户名

    @DefaultHandler
    public Resolution submitMessage() throws Exception {
        HttpSession session = getContext().getRequest().getSession();
        String yzcodeForSession = (String)session.getAttribute("randnum");
        if(yzcodeForSession.equals(letter.getYzcode())) {
            letter = filterParam(letter);
            searchmsg = getSearchmsgs();
            letter.setSearchmsg(searchmsg);
            if (letterService.insert(letter) != 0) {
                System.out.println("提交成功！");
                code = 1;
                String phones = letter.getPhone();
                String userids="sjsjxw";
                String pwd="sjsjxw123";
                String url="http://api.sms.hsyid.com:8088/SendMes.ashx";
                String content="尊敬的"+letter.getLinkman()+",您的信息已提交成功，请牢记您的六位查询码:"+searchmsg+",该查询码将与您所填写的手机号一起用于查询受理状态。【石景山区经信委】";
                XDTAPI xdtapi=new XDTAPI(userids, pwd, url);
                ResponseModel responseModel=xdtapi.SendMessage(content, phones);
                if(responseModel.Result==0){
                    System.out.println("提交成功，序列号="+responseModel.SeqId+",余额="+responseModel.Balance);
                }else{
                    System.out.println("提交失败，原因="+responseModel.Desc);
                }

            } else {
                System.out.println("提交失败！");
                code = 0;
            }
        }else{
            System.out.println("验证码错误");
            code = -1;
        }
        return new ForwardResolution("/zmhd/result.jsp");
    }

    private Letter filterParam(Letter letter){
        letter.setTitle(filter.excludeHTMLCode(letter.getTitle()));
        letter.setContent(filter.excludeHTMLCode(letter.getContent()));
        letter.setLinkman(filter.excludeHTMLCode(letter.getLinkman()));
        letter.setPhone(filter.excludeHTMLCode(letter.getPhone()));
        return letter;
    }


    private String getSearchmsgs(){
        String result="";
        String a = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] rands = new char[6];
        for (int i = 0; i < rands.length; i++)
        {
            int rand = (int) (Math.random() * a.length());
            rands[i] = a.charAt(rand);
        }
        for(int i=0;i<rands.length;i++){
            result += rands[i];
        }
        return result;
    }


    public Resolution getDepment() throws Exception{
        List<Letter> list=letterService.getDepartment();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return new StreamingResolution("application/json",json);
    }

    public Resolution getCategory() throws Exception{
        List<Letter> list=letterService.getCategory();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return new StreamingResolution("application/json",json);
    }


    public Resolution searchLetter() throws Exception {
        /*HttpSession session = getContext().getRequest().getSession();
        String yzcodeForSession = (String)session.getAttribute("randnum");

       if(yzcodeForSession.equals(letter.getYzcode())) {
            letter = filterParam(letter);
            if (letterService.insert(letter) != 0) {
                System.out.println("提交成功！");
                code = 1;
            } else {
                System.out.println("提交失败！");
                code = 0;
            }
        }else{
            System.out.println("验证码错误");
            code = -1;
        }*/


        Map<String, Object> param = new HashMap<String, Object>();
        if(searchId != 0)
            param.put("searchId",searchId);
        if(phone !=null && phone.length()>0)
            param.put("phone",phone);
        if(searchmsg !=null && searchmsg.length()>0)
            param.put("searchmsg",searchmsg);
        letter = letterService.getLetter(param);
        return new ForwardResolution("/zmhd/searchresult.jsp");
    }

    public Resolution getLetterList() throws Exception{
        System.out.println("获取信件列表:" + new Timestamp(System.currentTimeMillis()));
        Map<String, Object> param = new HashMap<String, Object>();
        if(userid!=null&&userid.length()>0){
            param.put("userid",userid);
            if(statusflag!=2) {
                param.put("statusflag", statusflag);
            }
        }else {
            param.put("selectedflag", selectedflag);
        }
        int count = letterService.countLetterList(param);
        if(pageSize<1 || pageSize>15) pageSize=15;
        int maxPage = (count % pageSize == 0) ? (count / pageSize) : (count / pageSize + 1);
        if(page<1) page=1;
        if(page>maxPage) page=maxPage;
        int startRec = (page - 1) * pageSize + 1;
        int endRec = page * pageSize;
        param.put("startRec", startRec);
        param.put("endRec", endRec);
        List<Letter> letterList = letterService.getLetterList(param);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("count", count);
        data.put("LetterList", letterList);
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return new StreamingResolution("application/json",json);
    }

    public Resolution getLetterbyId() throws Exception {
        System.out.println("获取信件内容:"+searchId+  new Timestamp(System.currentTimeMillis()));
        Map<String, Object> param = new HashMap<String, Object>();
        if(searchId != 0)
            param.put("searchId",searchId);
        letter = letterService.getLetter(param);
        System.out.println("标题："+letter.getTitle());
        Gson gson = new Gson();
        String json = gson.toJson(letter);
        return new StreamingResolution("application/json",json);
    }

    public LetterService getLetterService() {
        return letterService;
    }

    public void setLetterService(LetterService letterService) {
        this.letterService = letterService;
    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSearchmsg() {
        return searchmsg;
    }

    public void setSearchmsg(String searchmsg) {
        this.searchmsg = searchmsg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSelectedflag() {
        return selectedflag;
    }

    public void setSelectedflag(int selectedflag) {
        this.selectedflag = selectedflag;
    }

    public int getSearchId() {
        return searchId;
    }

    public void setSearchId(int searchId) {
        this.searchId = searchId;
    }

    public int getStatusflag() {
        return statusflag;
    }

    public void setStatusflag(int statusflag) {
        this.statusflag = statusflag;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
