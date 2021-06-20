package com.fzu.gcxl.daocloud.frontinterface.controller;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.AccountService;
import com.fzu.gcxl.daocloud.application.service.UserService;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoController {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @CrossOrigin
    @GetMapping("/user")
    public BaseResponse findUser(String userphone){
        return new BaseResponse(200, "用户信息", accountService.findUserByPhone(userphone));
    }

    @CrossOrigin
    @PostMapping("/user")
    public BaseResponse AddUser(@RequestBody JSONObject userinfo){
        return userService.createNewUser(userinfo);
    }

    @CrossOrigin
    @PutMapping("/user")
    public BaseResponse updateUser(@RequestBody JSONObject userinfo){
        return userService.updateUser(userinfo);
    }

    @CrossOrigin
    @DeleteMapping("/user")
    public BaseResponse deleteUser(String userphone){
        return userService.deleteUser(userphone);
    }
    @CrossOrigin
    @GetMapping("/userinfo")
    public BaseResponse getUserInfo(){
        return userService.getAllUser();
    }

    @CrossOrigin
    @PutMapping("/userinfo")
    public BaseResponse updateUserInfo(@RequestBody JSONObject userinfo){
        return userService.updateUserInfo(userinfo);
    }

    @CrossOrigin
    @GetMapping("/roleinfo")
    public BaseResponse updateUserInfo(Integer roleId){
        return userService.getInfoByRoleId(roleId);
    }

    @CrossOrigin
    @GetMapping("/studentinfo")
    public BaseResponse updateUserInfo(String classCode){
        return userService.getStudentInfoByClass(classCode);
    }
}
