package com.fzu.gcxl.daocloud.domain.persistence;

import com.fzu.gcxl.daocloud.domain.entity.Class;
import com.fzu.gcxl.daocloud.domain.entity.Role;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleRepository extends IBaseRepository<Role>{

    @Override
    int add(Role role);

    @Override
    int update(Role role);

    @Override
    int getCount(String search);

    @Override
    List<Role> getPage(String search, int start, int num);

    @Override
    Role getById(String id);

    @Override
    List<Role> getAll();

    @Override
    int delete(int id);

    Role findRoleById(Integer id);
}
