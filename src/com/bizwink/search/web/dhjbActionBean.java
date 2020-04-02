package com.bizwink.search.web;

import com.bizwink.search.domain.ReportGangdom;
import com.bizwink.search.service.DhjbService;
import com.bizwink.search.util.MD5Util;
import com.bizwink.search.util.filter;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.servlet.http.HttpSession;

/**
 * Created by kang on 2018/8/15.
 */
public class dhjbActionBean extends AbstractActionBean {

    @SpringBean
    DhjbService dhjbService;

    ReportGangdom reportGangdom;

    int code;

    @DefaultHandler
    public Resolution toIndex() throws Exception{
        return new RedirectResolution("../../");
    }

    public Resolution toReg()throws Exception{
        return new ForwardResolution("/sjsdhjb/sjsdhjb.jsp");
    }

    public Resolution submitMessage() throws Exception {
        HttpSession session = getContext().getRequest().getSession();
        String yzcodeForSession = (String)session.getAttribute("randnum");
        System.out.println(yzcodeForSession);
        System.out.println(reportGangdom.getYzcode());
        if(yzcodeForSession.equals(reportGangdom.getYzcode())) {
            reportGangdom = filterParam(reportGangdom);
            reportGangdom.setYanzhengmsg(Md5ReportGangdom(reportGangdom));
            if (dhjbService.submitMessage(reportGangdom) != 0) {
                System.out.println("提交成功！");
                code = 1;
            } else {
                System.out.println("提交失败！");
                code = 0;
            }
        }else{
            System.out.println("验证码错误");
            code = -1;
        }
            return new ForwardResolution("/sjsdhjb/result.jsp");
        //return new RedirectResolution("/dhjb.action");

    }

    private String Md5ReportGangdom(ReportGangdom reportGangdom){
        String str = reportGangdom.getJbrname()
                +reportGangdom.getSex()
                +reportGangdom.getIdcardno()
                +reportGangdom.getTelphone()
                +reportGangdom.getAddress()
                +reportGangdom.getPostcode()
                +reportGangdom.getReportedname()
                +reportGangdom.getEpithet()
                +reportGangdom.getRpaddress()
                +reportGangdom.getRpidcardno()
                +reportGangdom.getProvince()
                +reportGangdom.getCity()
                +reportGangdom.getCounty()
                +reportGangdom.getReportedcontent()
                +reportGangdom.getGzname()
                +reportGangdom.getUnittitle()
                +reportGangdom.getUnlevel()
                +reportGangdom.getGzreportedcontent()
                +reportGangdom.getIpadress();

        return MD5Util.MD5(str);
    }

    private ReportGangdom filterParam(ReportGangdom reportGangdom){
        reportGangdom.setJbrname(filter.excludeHTMLCode(reportGangdom.getJbrname()));
        reportGangdom.setSex(reportGangdom.getSex());
        reportGangdom.setIdcardno(filter.excludeHTMLCode(reportGangdom.getIdcardno()));
        reportGangdom.setTelphone(filter.excludeHTMLCode(reportGangdom.getTelphone()));
        reportGangdom.setAddress(filter.excludeHTMLCode(reportGangdom.getAddress()));
        reportGangdom.setPostcode(filter.excludeHTMLCode(reportGangdom.getPostcode()));
        reportGangdom.setReportedname(filter.excludeHTMLCode(reportGangdom.getReportedname()));
        reportGangdom.setEpithet(filter.excludeHTMLCode(reportGangdom.getEpithet()));
        reportGangdom.setRpaddress(filter.excludeHTMLCode(reportGangdom.getRpaddress()));
        reportGangdom.setRpidcardno(filter.excludeHTMLCode(reportGangdom.getRpidcardno()));
        reportGangdom.setProvince(filter.excludeHTMLCode(reportGangdom.getProvince()));
        reportGangdom.setCity(filter.excludeHTMLCode(reportGangdom.getCity()));
        reportGangdom.setCounty(filter.excludeHTMLCode(reportGangdom.getCounty()));
        reportGangdom.setReportedcontent(filter.excludeHTMLCode(reportGangdom.getReportedcontent()));
        reportGangdom.setGzname(filter.excludeHTMLCode(reportGangdom.getGzname()));
        reportGangdom.setUnittitle(filter.excludeHTMLCode(reportGangdom.getUnittitle()));
        reportGangdom.setUnlevel(reportGangdom.getUnlevel());
        reportGangdom.setGzreportedcontent(filter.excludeHTMLCode(reportGangdom.getGzreportedcontent()));
        return reportGangdom;
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

    public DhjbService getDhjbService() {

        return dhjbService;
    }

    public void setDhjbService(DhjbService dhjbService) {
        this.dhjbService = dhjbService;
    }
}