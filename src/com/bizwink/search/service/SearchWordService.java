package com.bizwink.search.service;

import com.bizwink.search.domain.SearchWord;
import com.bizwink.search.persistence.SearchWordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kang on 2019/7/15.
 */
@Service
public class SearchWordService {
    @Autowired
    private SearchWordMapper searchWordMapper;

    public List<SearchWord> getSearchWordByinput(String keyword){
        return  searchWordMapper.getSearchWordByinput(keyword);
    }

    public List<SearchWord> getRelatedWord(String sqlStr){
        return  searchWordMapper.getRelatedWord(sqlStr);
    }

    public List<SearchWord> getRightWord(String pinyin){
        return  searchWordMapper.getRightWord(pinyin);
    }

    public List<SearchWord> getHotWord(){
        return  searchWordMapper.getHotWord();
    }

    public List<SearchWord> getzmhd(String sqlStr){
        return  searchWordMapper.getzmhd(sqlStr);
    }
}
