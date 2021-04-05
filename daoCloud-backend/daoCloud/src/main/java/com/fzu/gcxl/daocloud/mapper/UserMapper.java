package com.fzu.gcxl.daocloud.mapper;

import com.fzu.gcxl.daocloud.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    User queryUserByName(String username);

    int insertUser(User user);


}
