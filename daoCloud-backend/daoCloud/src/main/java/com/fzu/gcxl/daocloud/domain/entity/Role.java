package com.fzu.gcxl.daocloud.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    private Integer roleId;
    // 角色名称
    private String roleName;
    // 创建时间
    private Date roleCreatetime;
    // 角色描述
    private String roleDescription;

    private String rolePerm;

    private List<User> userList;

    private List<Access> accessList;

}
