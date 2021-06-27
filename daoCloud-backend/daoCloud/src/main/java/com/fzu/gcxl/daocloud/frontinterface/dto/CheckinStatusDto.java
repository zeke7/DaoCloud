package com.fzu.gcxl.daocloud.frontinterface.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckinStatusDto {

    private Date checkinDate;

    private String isCheckin;

    private Integer checkinType;

}
