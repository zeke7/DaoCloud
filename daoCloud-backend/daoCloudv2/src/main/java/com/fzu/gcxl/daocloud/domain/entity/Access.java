package com.fzu.gcxl.daocloud.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Access {
    private Integer accessId;

    private String accessName;

    private String accessDetail;

    private Integer accessState;
}