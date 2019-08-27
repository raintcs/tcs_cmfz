package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter {
    private String id;
    private String title;
    private String size;
    private String duration;
    private Date upload_date;
    private String video;
    private String status;
    private String album_id;
}
