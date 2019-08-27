package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    @RequestMapping("/getAll")
    public Map<String, Object> getAll(Integer page, Integer rows) {
        Map<String, Object> map = bannerService.findByPage(page, rows);
        return map;
    }

    @RequestMapping("/edit")
    public Map<String, Object> edit(String oper, Banner banner, String[] id) {
        if ("add".equals(oper)) {
            Map<String, Object> map = bannerService.add(banner);
            return map;
        }
        if ("edit".equals(oper)) {
            bannerService.updateStatus(banner);
        }
        if ("del".equals(oper)) {
            bannerService.del(id);
        }
        return null;
    }

    /**
     * 上传图片
     */
    @RequestMapping("/upload")
    public void upload(String bannerId, MultipartFile image, HttpSession session) {
        //获取图片上传的路径
        String realPath = session.getServletContext().getRealPath("/banner");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = image.getOriginalFilename();
        String newFileName = System.currentTimeMillis() + "_" + filename;
        try {
            image.transferTo(new File(realPath, newFileName));

        } catch (IOException e) {
            e.printStackTrace();
        }
        Banner banner = new Banner();
        banner.setId(bannerId);
        banner.setImage(newFileName);
        bannerService.update(banner);
    }

}
