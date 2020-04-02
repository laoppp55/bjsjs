package com.bizwink.search.persistence;

import com.bizwink.search.domain.Article;

import java.util.List;
import java.util.Map;

public interface ZfgsMapper {

    List<Article> getArticleList(Map<String, Object> param);

    int countArticleList(Map<String, Object> param);
}
