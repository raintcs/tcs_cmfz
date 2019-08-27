package com.baizhi.controller;

import com.baizhi.service.KindeditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.Map;

@RestController
@RequestMapping("/kindeditor")
public class KindeditorController {
    @Autowired
    KindeditorService kindeditorService;

    @RequestMapping("/upload")
    public Map<String, Object> upload(MultipartFile imgFile, HttpServletRequest request) {
        Map<String, Object> map = kindeditorService.upload(imgFile, request);
        return map;
    }

    @RequestMapping("/allImages")
    public Map<String, Object> allImages(MultipartFile imgFile, HttpServletRequest request) throws UnknownHostException {
        Map<String, Object> map = kindeditorService.allImages(imgFile, request);
        return map;
    }
}

