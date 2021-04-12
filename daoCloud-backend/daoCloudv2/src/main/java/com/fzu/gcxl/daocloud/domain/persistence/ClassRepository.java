package com.fzu.gcxl.daocloud.domain.persistence;

import com.fzu.gcxl.daocloud.domain.entity.Account;
import com.fzu.gcxl.daocloud.domain.entity.Class;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassRepository  extends IBaseRepository<Class>{

    @Override
    int add(Class aClass);

    @Override
    int update(Class aClass);

    @Override
    int delete(int id);

    @Override
    int getCount(String search);

    Class findClassByName(String classname);

    Class findClassByNum(String classnum);

    int createNewClass(Class classObj);

    List<Class> getClassesByUser(String userPhone);

}
