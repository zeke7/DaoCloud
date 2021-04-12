package com.fzu.gcxl.daocloud.domain.persistence;

import com.fzu.gcxl.daocloud.domain.entity.Account;
import com.fzu.gcxl.daocloud.domain.entity.Class;
import com.fzu.gcxl.daocloud.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRepository extends IBaseRepository<User>{


    @Override
    int add(User user);

    @Override
    int update(User user);

    @Override
    int getCount(String search);

    @Override
    List<User> getPage(String search, int start, int num);

    @Override
    User getById(String id);

    @Override
    List<User> getAll();

    @Override
    int delete(int id);

    User findUserByPhone(String phone);
    User findUserByName(String name);
    int createNewUser(User user);

}
