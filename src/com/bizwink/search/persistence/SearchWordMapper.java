package com.bizwink.search.persistence;
import com.bizwink.search.domain.SearchWord;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SearchWordMapper {
    SearchWord getSearchWord(String cname);

    void insertSearchWord(SearchWord searchWord);

    void updateSearchWordNum(int id);

    List<SearchWord> getSearchWordByinput(String keyword);

    List<SearchWord> getRelatedWord(@Param("sqlStr")String sqlStr);

    List<SearchWord> getRightWord(String pinyin);

    List<SearchWord> getTabooWord();

    List<SearchWord> getHotWord();

    List<SearchWord> getzmhd(@Param("sqlStr")String sqlStr);

}

