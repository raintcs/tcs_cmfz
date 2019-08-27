package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao {

    List<Chapter> findAll(String id);

    /**
     * 分页
     */
    List<Chapter> findByPage(@Param("start") Integer start, @Param("rows") Integer rows, @Param("id") String id);

    /**
     * @param id 为对应的章节id即为album_id
     */
    Integer selectCount(String id);

    /**
     * 添加章节
     */
    void add(Chapter chapter);

    /**
     * 修改章节信息
     */
    void update(Chapter chapter);
}
