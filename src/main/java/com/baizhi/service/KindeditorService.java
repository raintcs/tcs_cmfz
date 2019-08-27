package com.baizhi.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.Map;

public interface KindeditorService {

    Map<String, Object> upload(MultipartFile fileName, HttpServletRequest request);

    Map<String, Object> allImages(MultipartFile fileName, HttpServletRequest request) throws UnknownHostException;
}
