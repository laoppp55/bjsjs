package com.bizwink.search.web;

import com.bizwink.search.domain.Archives;
import com.bizwink.search.service.ArchivesService;
import com.bizwink.search.util.CommUtil;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kang on 2018/11/23.
 */
public class archivesActionBean extends AbstractActionBean {
    @SpringBean
    ArchivesService archivesService;
    private String searchWord="";
    private int pageSize =50;
    private  int currentpage=1;
    private Map resultList;
    String startdate="";
    String enddate="";
    int page;
    String archivesid="";

    public int getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(int currentpage) {
        this.currentpage = currentpage;
    }



    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public Map getResultList() {
        return resultList;
    }

    public void setResultList(Map resultList) {
        this.resultList = resultList;
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

    public ArchivesService getArchivesService() {
        return archivesService;
    }

    public void setArchivesService(ArchivesService archivesService) {
        this.archivesService = archivesService;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public String getArchivesid() {
        return archivesid;
    }

    public void setArchivesid(String archivesid) {
        this.archivesid = archivesid;
    }

    @DefaultHandler
    public Resolution searchResult() throws Exception {
        if (this.searchWord == null || this.searchWord.length() == 0 || this.searchWord.equals("请输入搜索关键字")) {
            this.searchWord = "石景山";
        }
        this.searchWord = CommUtil.processXSS(this.searchWord);
        Map<String, Object> param = new HashMap<String, Object>();
        if(searchWord != null && searchWord.length()>0){
            param.put("searchWord",searchWord);
        }
        if(startdate != null && startdate.length()>0)
            param.put("startdate",startdate);
        if(enddate != null && enddate.length()>0)
            param.put("enddate",enddate);
        int count = archivesService.countArchivesList(param);
        /*if(pageSize<1 || pageSize>30) pageSize=15;
        int maxPage = (count % pageSize == 0) ? (count / pageSize) : (count / pageSize + 1);
        if(page<1) page=1;
        if(page>maxPage) page=maxPage;*/
        int startRec = this.pageSize * (this.currentpage - 1);
        int endRec = this.pageSize * this.currentpage;
        param.put("startRec", startRec);
        param.put("endRec", endRec);
        List<Archives> ArchivesList = archivesService.getArchivesList(param);
        this.resultList =new HashMap<String, Object>();
        this.resultList.put("count",count);
        this.resultList.put("ArchivesList",ArchivesList);
        return new ForwardResolution("searchArchives.jsp");
    }


    public Resolution searchResults() throws Exception {
        if (this.searchWord == null || this.searchWord.length() == 0 || this.searchWord.equals("请输入搜索关键字")) {
            this.searchWord = "石景山";
        }
        this.searchWord = CommUtil.processXSS(this.searchWord);
        Map<String, Object> param = new HashMap<String, Object>();
        if(searchWord != null && searchWord.length()>0){
            param.put("searchWord",searchWord);
        }
        this.archivesid = CommUtil.processXSS(this.archivesid);
        if(archivesid !=null && archivesid.length()>0){
            param.put("archivesid",archivesid);
        }
        int count = archivesService.countArchivessList(param);
        /*if(pageSize<1 || pageSize>30) pageSize=15;
        int maxPage = (count % pageSize == 0) ? (count / pageSize) : (count / pageSize + 1);
        if(page<1) page=1;
        if(page>maxPage) page=maxPage;*/
        int startRec = this.pageSize * (this.currentpage - 1);
        int endRec = this.pageSize * this.currentpage;
        param.put("startRec", startRec);
        param.put("endRec", endRec);
        List<Archives> ArchivesList = archivesService.getArchivessList(param);
        resultList =new HashMap<String, Object>();
        resultList.put("count",count);
        resultList.put("ArchivesList",ArchivesList);
        return new ForwardResolution("searchArchivess.jsp");
    }
}
