package com.fzu.gcxl.daocloud.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

// 账户表
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {

    private Integer accountId;
    // 登录手机号
    private String loginPhone;
    // 登录密码（md5）
    private String loginPassword;

    private User user;

}
