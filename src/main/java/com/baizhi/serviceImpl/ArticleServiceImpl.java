package com.baizhi.serviceImpl;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> findAllByPage(Integer page, Integer rows) {
        Integer start = (page - 1) * rows;
        List<Article> list = articleDao.findAll(start, rows);
        Integer count = articleDao.getCount();
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        Map<String, Object> map = new HashMap<>();
        map.put("rows", list);
        map.put("total", total);
        map.put("records", count);
        map.put("page", page);
        return map;
    }

    @Override
    public void add(Article article) {
        try {
            if ("" != article.getContent()) {
                article.setId(UUID.randomUUID().toString().replace("-", ""));
                article.setPublishDate(new Date());
                article.setUploadDate(new Date());
                articleDao.add(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Article article) {
        articleDao.update(article);
    }
}
