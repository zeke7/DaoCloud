package com.fzu.gcxl.daocloud.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    // 账号id
    private Integer accountId;
    // 验证码
    private String loginVerifyCode;
    // 注册手机
    private String loginPhone;
    // 注册密码
    private String loginPassword;

}
