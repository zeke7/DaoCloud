package com.fzu.gcxl.daocloud.controller;
import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.domain.persistence.AccountRepository;
import com.fzu.gcxl.daocloud.domain.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.entity.Account;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ModifyPwdController {

    @Autowired
    AccountRepository accountRepository;

    @CrossOrigin
    @PostMapping("/modifyPassword")
//    @RequiresPermissions(value = { "admin","normal" })
    public BaseResponse modifypwd(@RequestBody JSONObject usertomodfiypwd) {
        String username = usertomodfiypwd.getString("username");
        String newPassword = usertomodfiypwd.getString("newpassword");

        Account modPwd = new Account();
        modPwd.setLoginPhone(username);
        modPwd.setLoginPassword(newPassword);

        if (accountRepository.updateAccountPassword(modPwd) != 0){
            return new BaseResponse(HttpStatus.OK.value(), "密码修改成功", "ModifyPasswordSuccess");
        }else{
            return new BaseResponse(HttpStatus.OK.value(), "密码修改失败", "ModifyPasswordFail");
        }
    }


}
