package com.fzu.gcxl.daocloud.frontinterface.dto;

import com.fzu.gcxl.daocloud.domain.entity.Class;
import com.fzu.gcxl.daocloud.domain.entity.ClassInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassInfoStudentDto implements Serializable {

    private String userSchool;

    private String userDepartment;

    private String userName;

//    private String ;

    private List<ClassInfo> classinfos;

}
