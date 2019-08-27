package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface ChapterService {
    List<Chapter> findAll(String id);

    Map<String, Object> findByPage(Integer page, Integer rows, String id);

    String add(Chapter chapter);

    void update(Chapter chapter);


    Chapter upload(String chapterId, MultipartFile filename, HttpSession session);

    void download(String audioName, HttpServletResponse response, HttpSession session);
}
