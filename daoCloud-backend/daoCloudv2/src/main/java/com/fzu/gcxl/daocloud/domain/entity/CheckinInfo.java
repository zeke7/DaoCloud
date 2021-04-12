package com.fzu.gcxl.daocloud.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

// 签到信息表
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckinInfo implements Serializable {

    private Integer checkininfoid;
    // 签到课程号
    private String classnum;
    // 签到起始时间
    private Date starttime;
    // 签到结束时间
    private Date endtime;
    // 签到密码 (可为空)
    private String code;
    // 是否有效
    private Integer isvalid;

}
