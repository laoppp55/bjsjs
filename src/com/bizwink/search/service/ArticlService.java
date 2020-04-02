package com.bizwink.search.service;

import com.bizwink.search.domain.Article;
import com.bizwink.search.persistence.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jackzhang
 * Date: 13-10-15
 * Time: 下午12:22
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ArticlService {
    @Autowired
    private ArticleMapper articleMapper;

    public List<Article> getNeedIndex(){
        return   articleMapper.getNeedIndex(40);
    }
    public  Article getArticle(int id){
        return  articleMapper.getArticle(221)     ;
    }
}
