package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    @RequestMapping("/findAll")
    public List<Album> findAll() {
        List<Album> list = albumService.findAll();
        System.out.println(list);
        return list;
    }

    @RequestMapping("/queryByPage")
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        Map<String, Object> map = albumService.findByPage(page, rows);
        return map;
    }

    @RequestMapping("/edit")
    public Map<String, Object> edit(String oper, Album album, String[] id) {

        if ("add".equals(oper)) {
            Map<String, Object> map = albumService.add(album);
            return map;
        }
        if ("edit".equals(oper)) {
            albumService.updateStatus(album);
        }
        if ("del".equals(oper)) {
            albumService.del(id);
        }
        return null;
    }

    @RequestMapping("/upload")
    public void upload(String albumId, MultipartFile cover, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/album");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = cover.getOriginalFilename();
        String newFileName = System.currentTimeMillis() + "_" + filename;
        try {
            cover.transferTo(new File(realPath, newFileName));

        } catch (IOException e) {
            e.printStackTrace();
        }
        Album album = new Album();
        album.setId(albumId);
        album.setCover(newFileName);
        albumService.update(album);
    }

}
