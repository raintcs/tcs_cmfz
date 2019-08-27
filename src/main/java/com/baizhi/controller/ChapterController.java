package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    ChapterService chapterService;

    @RequestMapping("/findAll")
    public List<Chapter> findAll(String id) {
        List<Chapter> list = chapterService.findAll(id);
        return list;
    }

    /**
     * 分页分专辑展示章节
     */
    @RequestMapping("/findByPage")
    public Map<String, Object> findByPage(Integer page, Integer rows, String id) {
        Map<String, Object> map = chapterService.findByPage(page, rows, id);
        return map;
    }

    @RequestMapping("/edit")
    public String edit(String oper, Chapter chapter) {
        if ("add".equals(oper)) {
            String id = chapterService.add(chapter);
            return id;
        }
        return null;
    }

    @RequestMapping("/upload")
    public void upload(String chapterId, MultipartFile video, HttpSession session, String albumId) {
        Chapter chapter = chapterService.upload(chapterId, video, session);
        chapter.setAlbum_id(albumId);
        chapterService.update(chapter);
    }

    @RequestMapping("/download")
    public void download(String audioName, HttpServletResponse response, HttpSession session) {
        chapterService.download(audioName, response, session);
    }

}
