package com.fzu.gcxl.daocloud.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class School implements Serializable {

    private Integer school_id;

    private String school_name;

    private List<Department> departmentList;

}
