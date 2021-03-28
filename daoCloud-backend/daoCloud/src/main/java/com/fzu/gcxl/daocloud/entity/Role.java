package com.fzu.gcxl.daocloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    // 角色辨识id
    private Integer roleId;
    // 角色名
    private String roleName;
    // 角色权限
    private String roleAuthority;
}
