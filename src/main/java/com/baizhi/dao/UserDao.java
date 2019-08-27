package com.baizhi.dao;

import com.baizhi.entity.Banner;
import com.baizhi.entity.EchartsMap;
import com.baizhi.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    List<Banner> findByPage(Integer start, Integer rows);

    Integer selectCount();

    Integer queryAddressCount();

    List<EchartsMap> queryAddressAndCount();

    Integer selectByDayCount(Integer dayCount);
}
