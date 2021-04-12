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
public class DictionaryDetail {

  private Integer dicDetailId;

  private Integer dicTypeId;
  private String dicDetailCode;
  private String dicDetailName;
  private Integer dicDetailSerialNumber;
  private Integer dicDetailState;

}
