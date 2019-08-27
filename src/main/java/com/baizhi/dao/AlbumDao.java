package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    /**
     * 查询所有
     */
    List<Album> findAll();

    /**
     * 查询数据总数
     */
    Integer getCount();

    /**
     * 分页展示专辑
     *
     * @param start 起始行
     * @param rows  每页展示的数据数
     */
    List<Album> findByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    /**
     * 添加专辑
     */
    void add(Album album);

    /**
     * 修改专辑
     */
    void update(Album album);

    /**
     * 修改专辑状态
     */
    void updateStatus(Album album);

    /**
     * 批量删除专辑
     */
    void del(String[] id);
}
