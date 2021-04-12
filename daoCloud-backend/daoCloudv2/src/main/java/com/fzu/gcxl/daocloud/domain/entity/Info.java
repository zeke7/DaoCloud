package com.fzu.gcxl.daocloud.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info implements Serializable {

    private Integer info_id;
    // 发布信息者id
    private Integer user_id;
    // 查看权限
    private Integer role_id;
    // 通知内容
    private String info_content;
    // 通知类型
    private Integer info_type;

}
