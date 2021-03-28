package com.fzu.gcxl.daocloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private Integer userid;
    // 用户名
    private String username;
    // 昵称
    private String userNickname;
    // 所在学校
    private String userSchool;
    // 所在部门
    private String userDepartment;
    // 学号/工号
    private String userNo;
    // 用户手机
    private String userPhone;
    // 简介
    private String userDisp;
    // 用户角色 “1”->教师 “2”->学生
    private String userRole;

}
