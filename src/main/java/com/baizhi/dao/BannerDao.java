package com.baizhi.dao;


import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao {
    /**
     * 查询总数
     */
    Integer selectCount();

    /**
     * 分页展示数据
     *
     * @param start 起始行
     * @param rows  展示多少条
     */
    List<Banner> findByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    /**
     * 添加
     */
    void add(Banner banner);

    /**
     * 修改
     */
    void update(Banner banner);

    /**
     * 修改状态
     */
    void updateStatus(Banner banner);

    /**
     * 批量删除
     */
    void del(String[] id);
}
