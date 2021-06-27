package com.fzu.gcxl.daocloud.frontinterface.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckinStuDto {

    private String studentName;
    private String studentPhone;
    private String isCheckin;
}
