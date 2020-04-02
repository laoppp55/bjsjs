package com.bizwink.search.persistence;

import com.bizwink.search.domain.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-6-6
 * Time: 下午5:39
 * To change this template use File | Settings | File Templates.
 */
public interface ArticleMapper {
    Article getArticle(int id);
    List<Article> getNeedIndex(int siteid);
    List<Article> getDeleteIndex();
    //将该文章所在栏目及该栏目的所有父节点的id取出来拼成逗号字符串
    String getColumnParents(int id); //其父亲栏目id以逗号分隔组成一个字符串
    String getColumnDirname(int columnid);        //获取文章所造栏目的目录路径
    void updateIdxFlagByids(@Param(value = "ids") String  ids);
}
