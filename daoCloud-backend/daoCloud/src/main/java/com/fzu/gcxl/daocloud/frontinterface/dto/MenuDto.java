package com.fzu.gcxl.daocloud.frontinterface.dto;

import com.fzu.gcxl.daocloud.domain.entity.Submenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {
    private Integer menuId;

    private String name_en;

    private String nameZh;

    private Integer orderNum;

    private String url;

    private String icon;

    private List<Submenu> subMenusList;
}
