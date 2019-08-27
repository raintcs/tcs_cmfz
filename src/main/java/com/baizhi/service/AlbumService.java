package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    /**
     * 查所有
     */
    List<Album> findAll();

    /**
     * 分页展示数据
     *
     * @param page 当前展示第几页的数据
     * @param rows 展示几条数据
     */
    Map<String, Object> findByPage(Integer page, Integer rows);

    /**
     * 添加专辑
     */
    Map<String, Object> add(Album album);

    /**
     * 修改专辑
     */
    void update(Album album);

    void updateStatus(Album album);

    void del(String[] id);
}
