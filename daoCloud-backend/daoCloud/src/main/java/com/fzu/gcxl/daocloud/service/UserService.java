package com.fzu.gcxl.daocloud.service;

import com.fzu.gcxl.daocloud.entity.User;
import org.springframework.stereotype.Service;


public interface UserService {

    User queryUserByPhone(String userphone);

    int addUser(User user);
}
