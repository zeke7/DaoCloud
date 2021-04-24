package com.fzu.gcxl.daocloud.frontinterface.dto;

import com.fzu.gcxl.daocloud.domain.entity.Class;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassInfoDTO implements Serializable {

    private String userSchool;

    private String userDepartment;

    private String userName;

    private List<Class> classes;
}
