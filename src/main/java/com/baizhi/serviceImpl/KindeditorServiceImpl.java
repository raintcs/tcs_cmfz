package com.baizhi.serviceImpl;

import com.baizhi.service.KindeditorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class KindeditorServiceImpl implements KindeditorService {

    @Override
    public Map<String, Object> upload(MultipartFile fileName, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String realPath = request.getSession().getServletContext().getRealPath("/imgFile");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String originalFilename = fileName.getOriginalFilename();
        String newImgName = System.currentTimeMillis() + "_" + originalFilename;
        try {
            fileName.transferTo(new File(realPath, newImgName));
            //获取协议
            String scheme = request.getScheme();
            //获取本机的网络的ip地址
            InetAddress localHost = InetAddress.getLocalHost();
            String localhost = localHost.toString().split("/")[1];
            //获取端口号
            int port = request.getServerPort();
            //获取项目名
            String contextPath = request.getContextPath();
            //拼接url
            String url = scheme + "://" + localhost + ":" + port + contextPath + "/imgFile/" + newImgName;
            map.put("error", 0);
            map.put("url", url);
            return map;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Map<String, Object> allImages(MultipartFile fileName, HttpServletRequest request) throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        String realPath = request.getSession().getServletContext().getRealPath("/imgFile");
        File file = new File(realPath);
        String[] files = file.list();
        for (String s : files) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("is_dir", false);
            map1.put("has_file", false);
            //求文件大小
            File file1 = new File(realPath, s);
            long length = file1.length();
            map1.put("filesize", length);

            map1.put("dir_path", "");
            map1.put("is_photo", true);

            //取文件的后缀
            String substring = s.substring(s.lastIndexOf(".") + 1);
            map1.put("filetype", substring);

            map1.put("filename", s);

            //求时间
            boolean flag = s.contains("_");
            if (flag == true) {
                String s1 = s.split("_")[0];
                Long aLong = Long.valueOf(s1);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String s2 = format.format(aLong);
                map1.put("datetime", s2);
            }
            if (flag == false) {
                map1.put("datetime", new Date());
            }
            list.add(map1);
        }

        //获取协议
        String scheme = request.getScheme();
        //获取本机网络的id地址
        InetAddress localHost = InetAddress.getLocalHost();
        String localhost = localHost.toString().split("/")[1];
        //获取端口号
        int port = request.getServerPort();
        //获取项目名
        String contextPath = request.getContextPath();
        String url = scheme + "://" + localhost + ":" + port + contextPath + "/imgFile/";
        map.put("moveup_dir_path", "");
        map.put("current_dir_path", "");
        map.put("current_url", url);
        map.put("total_count", list.size());
        map.put("file_list", list);
        return map;
    }
}
