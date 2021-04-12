package com.fzu.gcxl.daocloud.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {


    private Integer userid;
    private Integer roleId;
    private String userName;
    private String userPhone;
    // 所在学校
    private String userSchool;
    // 学号/工号
    private String userSno;

    private String userDepartment;

    // 用户登录信息
//    private Account account;
//
//    private List<Class> classList;

}
