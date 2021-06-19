package com.fzu.gcxl.daocloud.frontinterface.dto;

import com.fzu.gcxl.daocloud.domain.entity.Dictionarydetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictionaryDto {

    // 大类别ID
    private Integer dicId;
    // 大类别编码
    private String dicCode;
    // 大类别名称
    private String dicName;
    // 大类别描述
    private String dicDiscription;
    // 大类别数据类型
    private String dicValue;
    // 字典细节
    private List<Dictionarydetail> detailist;

}
