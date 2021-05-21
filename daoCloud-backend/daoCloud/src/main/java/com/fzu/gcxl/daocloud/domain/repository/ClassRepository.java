package com.fzu.gcxl.daocloud.domain.repository;

import com.fzu.gcxl.daocloud.domain.entity.Class;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassRepository extends IRepository<Class>{

    int deleteByPrimaryKey(String classcode);

    int insert(Class record);

    int insertSelective(Class record);

    Class selectByPrimaryKey(String classcode);

    int updateByPrimaryKeySelective(Class record);

    int updateByPrimaryKey(Class record);

    Class selectByClassName(String classname);

    List<Class> selectAllClassesByUser(String userphone);

    List<Class> selectAllClasses();
}
