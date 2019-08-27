package com.baizhi.serviceImpl;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {

    @Autowired
    BannerDao bannerDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer selectCount() {
        Integer integer = bannerDao.selectCount();
        return integer;
    }

    @Override
    public Map<String, Object> add(Banner banner) {
        Map<String, Object> map = new HashMap<>();
        banner.setId(UUID.randomUUID().toString().replace("-", ""));
        banner.setCreate_date(new Date());
        bannerDao.add(banner);
        String id = banner.getId();
        map.put("id", id);
        return map;
    }

    @Override
    public void update(Banner banner) {
        bannerDao.update(banner);
    }

    @Override
    public void updateStatus(Banner banner) {
        bannerDao.updateStatus(banner);
    }

    @Override
    public void del(String[] id) {
        bannerDao.del(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> findByPage(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        Integer start = (page - 1) * rows;
        List<Banner> list = bannerDao.findByPage(start, rows);
        Integer count = bannerDao.selectCount();
        //total 总页数
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        //rows   数据集合
        map.put("rows", list);
        //total  总页数
        map.put("total", total);
        //page   page
        map.put("page", page);
        //records 总条数
        map.put("records", count);
        return map;
    }
}
