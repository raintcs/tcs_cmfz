package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.Map;

public interface BannerService {

    Integer selectCount();

    Map<String, Object> findByPage(Integer page, Integer rows);

    Map<String, Object> add(Banner banner);

    void update(Banner banner);

    void updateStatus(Banner banner);

    void del(String[] id);
}
