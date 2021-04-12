package com.fzu.gcxl.daocloud.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

// 签到表
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Checkin implements Serializable {

    private Integer checkinid;
    // 是否签到
    private Integer checkinis;
    // 签到日期
    private Date checkindate;
    // 签到用户
    private Integer userid;

    private String classnum;

    private Integer checkininfoid;

}
