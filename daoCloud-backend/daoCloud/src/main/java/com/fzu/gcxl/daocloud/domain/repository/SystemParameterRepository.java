package com.fzu.gcxl.daocloud.domain.repository;

import com.fzu.gcxl.daocloud.domain.entity.Class;
import com.fzu.gcxl.daocloud.domain.entity.SystemParameter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SystemParameterRepository extends IRepository<SystemParameter> {
    int deleteByPrimaryKey(Integer sysId);

    int insert(SystemParameter record);

    int insertSelective(SystemParameter record);

    SystemParameter selectByPrimaryKey(Integer sysId);

    int updateByPrimaryKeySelective(SystemParameter record);

    int updateByPrimaryKey(SystemParameter record);

    SystemParameter selectBysname(String sname);

    List<SystemParameter> getAllSysParams();
}