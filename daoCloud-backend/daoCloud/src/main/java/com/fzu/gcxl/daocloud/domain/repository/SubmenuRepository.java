package com.fzu.gcxl.daocloud.domain.repository;

import com.fzu.gcxl.daocloud.domain.entity.Submenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SubmenuRepository {
    int deleteByPrimaryKey(Integer menuId);

    int insert(Submenu record);

    int insertSelective(Submenu record);

    Submenu selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(Submenu record);

    int updateByPrimaryKey(Submenu record);

    List<Submenu> selectByParentId(Integer pid);
}