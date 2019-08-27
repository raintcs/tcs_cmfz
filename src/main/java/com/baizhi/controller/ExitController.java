package com.baizhi.controller;

import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ExitController {
    @Autowired
    AdminService adminService;

    @RequestMapping("/exit")
    public String exit() {
        return "redirect:/login/login.jsp";
    }

}
