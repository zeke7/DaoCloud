package com.fzu.gcxl.daocloud.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource implements Serializable {

    private Integer resource_id;
    // 资源号
    private String resource_num;
    // 发布者
    private Integer user_id;
    // 所属班课号
    private String class_num;
    // 资源名称
    private String resource_name;
    // 资源类型
    private String resource_type;
    // 发布时间
    private Date resource_s_date;
    // 失效时间
    private Date resource_e_date;


}
