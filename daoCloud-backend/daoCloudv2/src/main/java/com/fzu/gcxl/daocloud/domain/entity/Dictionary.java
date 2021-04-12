package com.fzu.gcxl.daocloud.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dictionary {

    private Integer dicId;
    private String dicCode;
    private String dicName;
    private String dicDiscription;
    private Integer dicDefaultId;

}
