package com.baizhi.service;

import com.baizhi.entity.EchartsMap;
import com.baizhi.entity.User;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> getAll(HttpSession session, HttpServletResponse response);

    Map<String, Object> findAllByPage(Integer page, Integer rows);

    List<EchartsMap> queryAddressAndCount();

    Map<String, Integer> selectByDayCount();
}
