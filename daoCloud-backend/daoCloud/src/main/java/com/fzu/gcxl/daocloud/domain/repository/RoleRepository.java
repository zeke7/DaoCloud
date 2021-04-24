package com.fzu.gcxl.daocloud.domain.repository;

import com.fzu.gcxl.daocloud.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleRepository extends IRepository<Role>{

    @Override
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(Role record);

    @Override
    int insertSelective(Role record);

    @Override
    Role selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(Role record);

    @Override
    int updateByPrimaryKey(Role record);

    Role findRoleById(Integer id);
}
