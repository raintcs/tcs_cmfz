package com.baizhi.serviceImpl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> login(String username, String password, String enCode, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        String code = (String) session.getAttribute("code");
        System.out.println("+++++++++++++++++++++++++++++" + code);
        //首先判断验证码
        if (enCode.equals(code)) {
            Admin admin = adminDao.login(username);
            if (admin != null) {
                if (password.equals(admin.getPassword())) {
                    map.put("message", "loginSuccess");
                    map.put("admin", admin);
                    session.setAttribute("admin", admin);
                    return map;
                } else {
                    map.put("message", "密码错误");
                    return map;
                }
            } else {
                map.put("message", "用户不存在");
                return map;
            }

        } else {
            map.put("message", "验证码错误");
            return map;
        }
    }
}
