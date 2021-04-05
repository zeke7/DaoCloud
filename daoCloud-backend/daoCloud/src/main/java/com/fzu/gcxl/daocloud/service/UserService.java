package com.fzu.gcxl.daocloud.service;

import com.fzu.gcxl.daocloud.entity.User;
import org.springframework.stereotype.Service;


public interface UserService {

    User queryUserByName(String username);

    int insertUser(User user);
}
