package com.fzu.gcxl.daocloud.application.service;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.domain.entity.Account;
import com.fzu.gcxl.daocloud.domain.entity.User;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.repository.AccountRepository;
import com.fzu.gcxl.daocloud.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    public Account findAccountByPhone(String username);

    public User findUserByPhone(String username);

    public int updateAccountPassword(Account account);

    public User findUserByName(String userphone);

    public BaseResponse createNewAccount(JSONObject usersignup);

    public BaseResponse resetPassword(JSONObject usertomodfiypwd);

    public BaseResponse backPassword(JSONObject usertomodfiypwd);

}
