package com.baizhi.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.baizhi.service.AdminService;
import com.baizhi.util.AliyunMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping("/login")
    public Map<String, Object> login(String username, String password, String enCode, HttpSession session) {
        Map<String, Object> map = adminService.login(username, password, enCode, session);
        return map;
    }

    @RequestMapping("/sendCode")
    public boolean sendMsg(String phoneNumber, HttpSession session) throws ClientException {
        int i = new Random().nextInt(1000000);
        String code = String.valueOf(i);
        session.setAttribute("code", code);
        System.out.println(code);
        String jsonContent = "{\"code\":\"" + code + "\"}";
        System.out.println(jsonContent);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phoneNumber", phoneNumber);
        paramMap.put("msgSign", "雨森RS");
        paramMap.put("templateCode", "SMS_172882427");
        paramMap.put("jsonContent", jsonContent);
        SendSmsResponse sendSmsResponse = AliyunMessageUtil.sendSms(paramMap);
        if (!(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK"))) {
            if (sendSmsResponse.getCode() == null) {
                //这里可以抛出自定义异常
                return false;
            }
            if (!sendSmsResponse.getCode().equals("OK")) {
                //这里可以抛出自定义异常
                return false;
            }
            return true;
        }
        return false;
    }
}
