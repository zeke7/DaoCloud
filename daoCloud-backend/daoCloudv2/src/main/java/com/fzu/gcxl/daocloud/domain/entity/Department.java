package com.fzu.gcxl.daocloud.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Serializable {

    private Integer department_id;

    private String department_name;

    private List<School> schoolList;

}
