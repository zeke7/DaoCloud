package com.fzu.gcxl.daocloud.frontinterface.controller;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.AccountService;
import com.fzu.gcxl.daocloud.domain.entity.Account;
import com.fzu.gcxl.daocloud.domain.repository.AccountRepository;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetPwdController {

    @Autowired
    private AccountService accountService;

    @CrossOrigin
    @PostMapping("/resetpassword")
//    @RequiresPermissions(value = { "admin","normal" })
    public BaseResponse modifypwd(@RequestBody JSONObject usertomodfiypwd) {
       return accountService.resetPassword(usertomodfiypwd);
    }

    // 找回密码
    @CrossOrigin
    @PostMapping("/backpassword")
//    @RequiresPermissions(value = { "admin","normal" })
    public BaseResponse findpwdback(@RequestBody JSONObject usertomodfiypwd) {
        return accountService.backPassword(usertomodfiypwd);
    }

}
