package com.bizwink.search.web;
import com.bizwink.search.domain.ReportGangdom;
import com.bizwink.search.service.JwdhService;
import com.bizwink.search.util.MD5Util;
import com.bizwink.search.util.filter;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.integration.spring.SpringBean;


public class jwdhActionBean extends AbstractActionBean {

    @SpringBean
    JwdhService jwdhService;

    ReportGangdom reportGangdom;

    int code;
    String searchmsg;

    @DefaultHandler
    public Resolution toIndex() throws Exception{
        return new RedirectResolution("../../");
    }

    public Resolution toResult()throws Exception{
        return new ForwardResolution("/sjsjwdhjb/result.jsp");
    }

    public Resolution submitMessage() throws Exception {
        reportGangdom = filterParam(reportGangdom);
        String Md5str = Md5ReportGangdom(reportGangdom);
        if(jwdhService.getReportGangdom(Md5str) > 0 ){
            code = -1;
        }else {
            reportGangdom.setYanzhengmsg(Md5str);
            searchmsg = getSearchmsgs();
            reportGangdom.setSearchmsg(searchmsg);
            if (jwdhService.submitMessage(reportGangdom) != 0) {
                System.out.println("提交成功！");
                code = 1;
            } else {
                System.out.println("提交失败！");
                code = 0;
            }

            //return new RedirectResolution("/jwdh.action?toResult()");
        }
        return new ForwardResolution("/sjsjwdhjb/result.jsp");

    }

    private String Md5ReportGangdom(ReportGangdom reportGangdom){
        String str = reportGangdom.getJbrname()
                +reportGangdom.getIdcardno()
                +reportGangdom.getJbrlink()
                +reportGangdom.getJbrpolitical()
                +reportGangdom.getAddress()
                +reportGangdom.getJbrlevel()
                +reportGangdom.getReportedname()
                +reportGangdom.getDepartment()
                +reportGangdom.getRpJob()
                +reportGangdom.getCounty()
                +reportGangdom.getRplevel()
                +reportGangdom.getRepmaintitle()
                +reportGangdom.getRepclass()
                +reportGangdom.getRepclasses()
                +reportGangdom.getReportedcontent()
                +reportGangdom.getFilename()
                +reportGangdom.getIpadress();
        return MD5Util.MD5(str);
    }

    private ReportGangdom filterParam(ReportGangdom reportGangdom){
        reportGangdom.setJbrname(filter.excludeHTMLCode(reportGangdom.getJbrname()));
        reportGangdom.setIdcardno(filter.excludeHTMLCode(reportGangdom.getIdcardno()));
        reportGangdom.setJbrlink(filter.excludeHTMLCode(reportGangdom.getJbrlink()));
        reportGangdom.setAddress(filter.excludeHTMLCode(reportGangdom.getAddress()));
        reportGangdom.setReportedname(filter.excludeHTMLCode(reportGangdom.getReportedname()));
        reportGangdom.setDepartment(filter.excludeHTMLCode(reportGangdom.getDepartment()));
        reportGangdom.setRpJob(filter.excludeHTMLCode(reportGangdom.getRpJob()));
        reportGangdom.setRepmaintitle(filter.excludeHTMLCode(reportGangdom.getRepmaintitle()));
        reportGangdom.setReportedcontent(filter.excludeHTMLCode(reportGangdom.getReportedcontent()));
        return reportGangdom;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ReportGangdom getReportGangdom() {
        return reportGangdom;
    }

    public void setReportGangdom(ReportGangdom reportGangdom) {
        this.reportGangdom = reportGangdom;
    }

    public JwdhService getJwdhService() {
        return jwdhService;
    }

    public void setJwdhService(JwdhService jwdhService) {
        this.jwdhService = jwdhService;
    }

    public String getSearchmsg() {
        return searchmsg;
    }

    public void setSearchmsg(String searchmsg) {
        this.searchmsg = searchmsg;
    }


}