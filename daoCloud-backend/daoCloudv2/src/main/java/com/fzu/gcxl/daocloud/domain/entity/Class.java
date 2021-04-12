package com.fzu.gcxl.daocloud.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class implements Serializable {

    private Integer classId;
    // 班课创建者 对应user_id
    private String userPhone;

    private String className;
    // 班课成员数量
    private Integer classMember;
    // 班课号
    private String classNum;

//    private List<User> studentList;

}
