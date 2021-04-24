package com.fzu.gcxl.daocloud.domain.repository;

import com.fzu.gcxl.daocloud.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRepository extends IRepository<User>{


    @Override
    int deleteByPrimaryKey(Integer id);

    @Override
    int insert(User record);

    @Override
    int insertSelective(User record);

    @Override
    User selectByPrimaryKey(Integer id);

    @Override
    int updateByPrimaryKeySelective(User record);

    @Override
    int updateByPrimaryKey(User record);

    User findUserByPhone(String phone);
    User findUserByName(String name);
    int createNewUser(User user);

}
