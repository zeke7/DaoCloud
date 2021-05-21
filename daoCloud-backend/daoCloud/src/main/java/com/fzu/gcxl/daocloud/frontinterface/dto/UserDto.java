package com.fzu.gcxl.daocloud.frontinterface.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer roleId;
    private String userName;
    private String userPhone;
    // 所在学校
    private String userSchool;
    // 学号/工号
    private String userSno;

    private String userDepartment;
}
