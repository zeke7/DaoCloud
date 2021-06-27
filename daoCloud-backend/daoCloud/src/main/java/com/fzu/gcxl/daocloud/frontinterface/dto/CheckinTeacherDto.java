package com.fzu.gcxl.daocloud.frontinterface.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckinTeacherDto {

    private Date checkinDate;

    private Integer checkinType;

    List<CheckinStuDto> studentList;

}
