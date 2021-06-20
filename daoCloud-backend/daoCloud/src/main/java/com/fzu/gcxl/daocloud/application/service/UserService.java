package com.fzu.gcxl.daocloud.application.service;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.domain.entity.User;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User findUserByPhone(String userphone);

    public BaseResponse createNewUser(JSONObject newUser);

    public BaseResponse deleteUser(String userphone);

    public BaseResponse updateUserInfo(JSONObject createClass);

    public BaseResponse getInfoByRoleId(Integer roleId);

    public BaseResponse getStudentInfoByClass(String classCode);

    public BaseResponse getAllUser();

    public BaseResponse updateUser(JSONObject userInfo);


}
