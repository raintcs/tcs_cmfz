package com.baizhi.serviceImpl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumDao albumDao;

    /**
     * 查所有
     */
    @Override
    public List<Album> findAll() {
        List<Album> list = albumDao.findAll();
        return list;
    }

    @Override
    public void update(Album album) {
        albumDao.update(album);
    }

    @Override
    public void updateStatus(Album album) {
        albumDao.updateStatus(album);
    }

    @Override
    public void del(String[] id) {
        albumDao.del(id);
    }

    /**
     * 添加专辑
     */
    @Override
    public Map<String, Object> add(Album album) {
        Map<String, Object> map = new HashMap<>();
        album.setId(UUID.randomUUID().toString().replace("-", ""));
        album.setPublish_date(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse("2018-08-08");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        album.setUpload_date(date);
        albumDao.add(album);
        String id = album.getId();
        System.out.println("**********************" + album.getId());
        map.put("id", id);
        return map;
    }

    /**
     * 分页展示数据
     *
     * @param page 当前展示第几页的数据
     * @param rows 每页展示几条数据
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findByPage(Integer page, Integer rows) {
        Integer start = (page - 1) * rows;
        /*list相当于rows总记录数*/
        List<Album> list = albumDao.findByPage(start, rows);
        /*count数据总条数*/
        Integer count = albumDao.getCount();
        /*计算页数total*/
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", list);
        map.put("total", total);
        map.put("records", count);
        return map;
    }
}
