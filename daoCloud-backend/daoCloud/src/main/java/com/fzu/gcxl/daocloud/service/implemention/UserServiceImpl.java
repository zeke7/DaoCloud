package com.fzu.gcxl.daocloud.service.implemention;

import com.fzu.gcxl.daocloud.entity.User;
import com.fzu.gcxl.daocloud.mapper.UserMapper;
import com.fzu.gcxl.daocloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User queryUserByPhone(String userphone) {
        return userMapper.queryUserByPhone(userphone);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }
}
