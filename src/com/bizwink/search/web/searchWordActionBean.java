package com.bizwink.search.web;

import com.bizwink.search.domain.SearchWord;
import com.bizwink.search.service.SearchWordService;
import com.bizwink.search.util.PinyinDemo;
import com.bizwink.search.util.WriteTxt;
import com.google.gson.Gson;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class searchWordActionBean extends AbstractActionBean {

    @SpringBean
    SearchWordService searchWordService;
    String keyword;


    @DefaultHandler
    public Resolution getSearchWordByinput() throws Exception {
        List<SearchWord> resultList = new ArrayList<>();
        if(keyword!=null&&keyword.length()>0) {
            resultList = searchWordService.getSearchWordByinput(keyword);
        }
        Gson gson = new Gson();
        String json = gson.toJson(resultList);
        return new StreamingResolution("application/json",json);
    }

    //相关搜索
    public Resolution getRelatedWord() throws Exception {
        //Map<String, Object> param = new HashMap<String, Object>();
        List<SearchWord> resultList = new ArrayList<>();
        if(keyword!=null&&keyword.length()>0) {
            String sqlStrstart =" and (";
            String sqlStrend = ")";
            String sqlStr="";
            List<String> words = splitWord(keyword, true); // 显示拆分结果
            for(int i=0;i<words.size();i++){
                if(i > 0) {
                    sqlStr = sqlStr + " or ";
                }
                sqlStr = sqlStr + " t.cname like  CONCAT(CONCAT('%', '"+words.get(i)+"'), '%')  or t.ename like  CONCAT(CONCAT('%', '"+words.get(i)+"'), '%') or t.SNAME like  CONCAT(CONCAT('%', '"+words.get(i)+"'), '%') ";
            }
            sqlStr = sqlStrstart+sqlStr+sqlStrend;
            //System.out.println("相关搜索:"+sqlStr);
           // param.put("words",words);
            resultList = searchWordService.getRelatedWord(sqlStr);
        }
        Gson gson = new Gson();
        String json = gson.toJson(resultList);
        return new StreamingResolution("application/json",json);
    }

    //纠错推荐搜索
    public Resolution getRightWord() throws Exception {
        List<SearchWord> resultList = new ArrayList<>();
        if(keyword!=null&&keyword.length()>0) {
            String pinyin = PinyinDemo.ToPinyin(keyword);
            resultList = searchWordService.getRightWord(pinyin);
        }
        Gson gson = new Gson();
        String json = gson.toJson(resultList);
        return new StreamingResolution("application/json",json);
    }

   //获取政民互动
   public Resolution getzmhd() throws Exception {
       //Map<String, Object> param = new HashMap<String, Object>();
       List<SearchWord> resultList = new ArrayList<>();
       if(keyword!=null&&keyword.length()>0) {
           String sqlStrstart =" and (";
           String sqlStrend = ")";
           String sqlStr="";
          /* List<String> words = splitWord(keyword, true); // 显示拆分结果
           for(int i=0;i<words.size();i++){
               if(i > 0) {
                   sqlStr = sqlStr + " or ";
               }
               sqlStr = sqlStr + "t.TITLE like CONCAT(CONCAT('%', '"+words.get(i)+"'), '%') or t.content like  CONCAT(CONCAT('%', '"+words.get(i)+"'), '%')";

           }*/
           sqlStr = sqlStr + "t.TITLE like CONCAT(CONCAT('%', '"+ keyword+"'), '%') or t.content like  CONCAT(CONCAT('%', '"+ keyword+"'), '%')";
           sqlStr = sqlStrstart+sqlStr+sqlStrend;
           System.out.println(sqlStr);
          //param.put("words",words);
           resultList = searchWordService.getzmhd(sqlStr);
       }
       Gson gson = new Gson();
       String json = gson.toJson(resultList);
       return new StreamingResolution("application/json",json);
   }




    //推荐热搜词
    public Resolution getHotWord() throws Exception {
        List<SearchWord> resultList = searchWordService.getHotWord();
        Gson gson = new Gson();
        String json = gson.toJson(resultList);
        return new StreamingResolution("application/json",json);
    }

    /**
     * 查看IKAnalyzer 分词器是如何将一个完整的词组进行分词的
     *
     * @param text
     * @param isMaxWordLength
     */
    public static List<String> splitWord(String text, boolean isMaxWordLength) {
        List<String> keywords = null;
        try {
            /* 创建分词对象 */
            // 遍历分词数据
            System.out.print("IKAnalyzer把关键字拆分的结果是：");
            keywords = new ArrayList<String>();
            Configuration configuration = DefaultConfig.getInstance();
            configuration.setUseSmart(true);
            StringReader reader = new StringReader(text);
            IKSegmenter ik = new IKSegmenter(reader, configuration);
            Lexeme lexeme = null;
            if(text.length()>=2) {
                WriteTxt.writeFile(text);
            }
            while ((lexeme = ik.next()) != null) {
                keywords.add(lexeme.getLexemeText());
                System.out.print("【" + lexeme.getLexemeText() + "】");
                if(lexeme.getLexemeText().length()>=2)
                    WriteTxt.writeFile(lexeme.getLexemeText());
            }
            System.out.println("\r\n");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keywords;
    }

    public SearchWordService getSearchWordService() {
        return searchWordService;
    }

    public void setSearchWordService(SearchWordService searchWordService) {
        this.searchWordService = searchWordService;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
