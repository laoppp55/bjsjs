package com.bizwink.search.web;

import com.bizwink.search.domain.Article;
import com.bizwink.search.service.ZfgsService;
import com.bizwink.search.util.CommUtil;
import com.google.gson.Gson;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class zfgsActionBean extends AbstractActionBean {

    @SpringBean
    ZfgsService zfgsService;
    int page;
    int pageSize;
    int columnid =-1;
    String source="";

    @DefaultHandler
    public Resolution getArticleList() throws Exception{

        Map<String, Object> param = new HashMap<String, Object>();
        if(columnid != -1){
            param.put("columnid", columnid);
        }
        if(source != null && source.length()>0) {
            System.out.println(source);
            source = CommUtil.processXSS(source);
            String source1 = "";
            String source2 = "";
            if(source.equals("发展改革委")){
                source1 = "区发展和改革委员会";
                source2 = "发展改革委";
            }
            if(source.equals("教委")){
                source1 = "区教育委员会";
                source2 = "教委";
            }
            if(source.equals("科委")){
                source1="区科学技术委员会";
                source2="科委";
            }
            if(source.equals("经济和信息化局")){
                source1="区经济和信息化局";
                source2="经济和信息化局";
            }
            if(source.equals("民政局")){
                source1="民政局";
            }
            if(source.equals("司法局")){
                source1="石景山区司法局";
                source2 ="司法局";
            }
            if(source.equals("财政局")){
                source1="财政局";
            }
            if(source.equals("人力资源社会保障局")){
                source1="区人力资源和社会保障局";
                source2="人力资源社会保障局";
            }
            if(source.equals("生态环境局")){
                source1="生态环境局";
            }
            if(source.equals("住房城市建设委")){
                source1="区住房城市建设委";
            }
            if(source.equals("城市管理委")){
                source1 = "城管委";
                source2 = "城市管理委";
            }
            if(source.equals("水务局")){
                source1="水务局";
                source2="水务局";
            }
            if(source.equals("商务局")){
                source1="区商务局";
                source2="商务局";
            }
            if(source.equals("文化和旅游局")){
                source1="区文旅局";
                source2="文化和旅游局";
            }
            if(source.equals("卫生健康委")){
               source1="卫生健康";
               source2="卫生健康委";
            }
            if(source.equals("退役军人局")){
                source1="退役军人事务局";
                source2="退役军人局";
            }
            if(source.equals("应急局")){
               source1="应急局";
               source2="应急局";
            }
            if(source.equals("市场监管局")){
                source1 ="市场监管局";
                source2 ="市场监管局";
            }
            if(source.equals("审计局")){
                source1="审计局";
                source2="审计局";
            }
            if(source.equals("体育局")){
                source1="体育局";
                source2="体育局";
            }
            if(source.equals("统计局")){
               source1="统计局";
                source2="统计局";
            }
            if(source.equals("园林绿化局")){
                source1="园林绿化局";
                source2="园林绿化局";
            }
            if(source.equals("人防办")){
                source1="人防办";
                source2="人防办";
            }
            if(source.equals("政府集体资产监管办")){
                source1="集体资产监管办";
                source2="政府集体资产监管办";
            }
            if(source.equals("医保局")){
                source1 ="医保局";
                source2="医保局";
            }
            if(source.equals("档案局")){
                source1="档案局";
                source2="档案馆";
            }
            if(source.equals("公务员局")){ //未知单位
                source1="公务员局";
                source2="公务员局";
            }
            if(source.equals("新闻出版局")){ //未知单位
                source1="新闻出版局";
                source2="新闻出版局";
            }
            if(source.equals("民族宗教侨务办")){ //未知单位
                source1="民族宗教侨务办";
                source2="民族宗教侨务办";
            }
            if(source.equals("网信办")){
               source1="网信办";
               source2="网信办";
            }
            if(source.equals("城管执法局")){ //需确认
                source1="城市管理综合行政执法监察局";
                source2="城管执法局";
            }
            if(source.equals("八宝山街道办事处")){
                source1="八宝山街道";
                source2="八宝山街道办事处";
            }
            if(source.equals("八角街道办事处")){
                source1="八角街道";
                source2="八角街道办事处";
            }
            if(source.equals("古城街道办事处")){
                source1="古城街道";
                source2="古城街道办事处";
            }
            if(source.equals("苹果园街道办事处")){
                source1="苹果园街道";
                source2="苹果园街道办事处";
            }
            if(source.equals("金顶街街道办事处")){
                source1="金顶街街道";
                source2="金顶街街道办事处";
            }
            if(source.equals("老山街道办事处")){
                source1="老山街道";
                source2="老山街道办事处";
            }
            if(source.equals("广宁街道办事处")){
               source1="广宁街道";
                source2="广宁街道办事处";
            }
            if(source.equals("五里坨街道办事处")){
                source1="五里坨街道";
                source2="五里坨街道办事处";
            }
            if(source.equals("鲁谷街道办事处")){
               source1="鲁谷街道";
               source2="鲁谷街道办事处";
            }

            /*String sourceSql= " and ( t.source like CONCAT(CONCAT('%', '"+ source1+"'), '%')";
            if(source2!=null && source2.length()>0){
                sourceSql = sourceSql + " or t.source like  CONCAT(CONCAT('%', '"+ source2+"'), '%'))";
            }*/
            //System.out.println(sourceSql);
           // param.put("sourceSql", sourceSql);
            if(source1!=null && source1.length()>0)
                 param.put("source1",source1);
            if(source2!=null && source1.length()>0)
                 param.put("source2",source2);

        }
        int count = zfgsService.countArticleList(param);
        if(pageSize<1 || pageSize>30) pageSize=20;
        int maxPage = (count % pageSize == 0) ? (count / pageSize) : (count / pageSize + 1);
        if(page<1) page=1;
        if(page>maxPage) page=maxPage;
        int startRec = (page - 1) * pageSize + 1;
        int endRec = page * pageSize;
        param.put("startRec", startRec);
        param.put("endRec", endRec);
        List<Article> ArticleList = zfgsService.getArticleList(param);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("count", count);
        data.put("ArticleList", ArticleList);
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return new StreamingResolution("application/json",json);


    }

    public ZfgsService getZfgsService() {
        return zfgsService;
    }

    public void setZfgsService(ZfgsService zfgsService) {
        this.zfgsService = zfgsService;
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

    public int getColumnid() {
        return columnid;
    }

    public void setColumnid(int columnid) {
        this.columnid = columnid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
