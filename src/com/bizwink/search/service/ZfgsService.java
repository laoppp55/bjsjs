package com.bizwink.search.service;

import com.bizwink.search.persistence.ZfgsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bizwink.search.domain.Article;

import java.util.List;
import java.util.Map;


@Service
public class ZfgsService {
    @Autowired
    private ZfgsMapper zfgsMapper;

    public int countArticleList(Map<String, Object> param){
        return  zfgsMapper.countArticleList(param);
    }

    public List<Article> getArticleList(Map<String, Object> param){
        return zfgsMapper.getArticleList(param);
    }
}
