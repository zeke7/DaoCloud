package com.fzu.gcxl.daocloud.frontinterface.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckinSingleDto {
    private String studentName;
    private String studentPhone;
    CheckinStatusDto statusDto;
}
