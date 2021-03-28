package com.fzu.gcxl.daocloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private Integer departmentId;
    private String  departmentName;
}
