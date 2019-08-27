package com.baizhi.serviceImpl;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterDao chapterDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> findByPage(Integer page, Integer rows, String id) {
        Map<String, Object> map = new HashMap<>();
        Integer start = (page - 1) * rows;
        List<Chapter> list = chapterDao.findByPage(start, rows, id);
        Integer count = chapterDao.selectCount(id);
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        map.put("rows", list);
        map.put("total", total);
        map.put("page", page);
        map.put("records", count);
        return map;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Chapter> findAll(String id) {
        List<Chapter> list = chapterDao.findAll(id);
        return list;
    }

    @Override
    public String add(Chapter chapter) {

        chapter.setId(UUID.randomUUID().toString().replace("-", ""));
        chapter.setUpload_date(new Date());
        String id = chapter.getId();
        System.out.println(id + "---------------------11111-------------------------");
        chapterDao.add(chapter);
        return id;
    }


    @Override
    public void update(Chapter chapter) {

        chapterDao.update(chapter);
    }

    @Override
    public Chapter upload(String chapterId, MultipartFile filename, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/audio");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String originalFilename = filename.getOriginalFilename();
        String newFileName = System.currentTimeMillis() + "_" + originalFilename;
        File file1 = new File(realPath, newFileName);
        try {
            filename.transferTo(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //计算文件的大小 结果是字节
        long l = filename.getSize();
        String size = l / 1024 / 1024 + "MB";
        try {
            AudioFile read = AudioFileIO.read(file1);
            AudioHeader audioHeader = read.getAudioHeader();
            //得到的时长是秒
            int trackLength = audioHeader.getTrackLength();
            String m = trackLength / 60 + "分";
            String s = trackLength % 60 + "秒";
            Chapter chapter = new Chapter();
            chapter.setId(chapterId);
            chapter.setVideo(newFileName);
            chapter.setSize(size);
            chapter.setDuration(m + s);
            return chapter;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void download(String audioName, HttpServletResponse response, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/audio");
        File file = new File(realPath, audioName);
        String s = audioName.toString().split("_")[1];
        ServletOutputStream outputStream = null;
        try {
            String encode = URLEncoder.encode(s, "utf-8");
            String replace = encode.replace("+", "%20");
            response.setHeader("content-disposition", "attachment;filename=" + replace);
            outputStream = response.getOutputStream();
            FileUtils.copyFile(file, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
