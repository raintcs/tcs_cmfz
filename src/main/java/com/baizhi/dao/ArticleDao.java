package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {
    List<Article> findAll(@Param("start") Integer start, @Param("rows") Integer rows);

    Integer getCount();

    void add(Article article);

    void update(Article article);

}
