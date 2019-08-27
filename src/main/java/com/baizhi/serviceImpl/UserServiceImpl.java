package com.baizhi.serviceImpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Banner;
import com.baizhi.entity.EchartsMap;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> getAll(HttpSession session, HttpServletResponse response) {
        String realPath = session.getServletContext().getRealPath("/imgUser/");
        List<User> list = userDao.getAll();
        for (User user : list) {
            String icon = user.getIcon();
            System.out.println(icon);
            user.setIcon(realPath + icon);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息", "用户"), User.class, list);
        ServletOutputStream outputStream = null;
        try {
            String encode = URLEncoder.encode("用户表.xls", "utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + encode);
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public Map<String, Object> findAllByPage(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        Integer start = (page - 1) * rows;
        List<Banner> list = userDao.findByPage(start, rows);
        Integer count = userDao.selectCount();
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

    @Override
    public List<EchartsMap> queryAddressAndCount() {
        List<EchartsMap> list = userDao.queryAddressAndCount();
        for (EchartsMap echartsMap : list) {
            System.out.println(echartsMap);
        }
        return list;
    }

    @Override
    public Map<String, Integer> selectByDayCount() {
        Map<String, Integer> map = new HashMap<>();
        map.put("one", userDao.selectByDayCount(5));
        map.put("two", userDao.selectByDayCount(10));
        map.put("three", userDao.selectByDayCount(20));
        map.put("four", userDao.selectByDayCount(21));
        map.put("five", userDao.selectByDayCount(22));
        map.put("six", userDao.selectByDayCount(25));
        map.put("seven", userDao.selectByDayCount(30));
        return map;
    }
}
