package com.baizhi.controller;

import com.baizhi.entity.EchartsMap;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/getAll")
    public Map<String, Object> getAllByPage(Integer page, Integer rows) {
        Map<String, Object> map = userService.findAllByPage(page, rows);
        return map;
    }

    @RequestMapping("/exportAll")
    public void outData(HttpSession session, HttpServletResponse response) {
        userService.getAll(session, response);
    }

    @RequestMapping("/getByDayCount")
    public Map<String, Integer> getByDayCount() {
        Map<String, Integer> map = userService.selectByDayCount();
        return map;
    }

    @RequestMapping("/testMap")
    public List<EchartsMap> testMap() {
        List<EchartsMap> list = userService.queryAddressAndCount();
        return list;
    }
}
