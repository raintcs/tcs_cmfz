package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @ExcelIgnore
    private String id;
    @Excel(name = "名字")
    private String name;
    @ExcelIgnore
    private String password;
    @Excel(name = "手机号")
    private String phone;
    @Excel(name = "头像", type = 2, width = 40, height = 30)
    private String icon;
    @Excel(name = "法号")
    private String dharma;
    @Excel(name = "性别")
    private String sex;
    @Excel(name = "状态")
    private String status;
    @Excel(name = "创建时间", format = "yyyy-MM-dd", width = 30)
    private Date createDate;
}
