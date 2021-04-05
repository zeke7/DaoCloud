package com.fzu.gcxl.daocloud.controller;
import com.fzu.gcxl.daocloud.entity.Account;
import com.fzu.gcxl.daocloud.config.JWTPackage.JwtUtils;
import com.fzu.gcxl.daocloud.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.service.implemention.AccountServiceImpl;
import com.fzu.gcxl.daocloud.service.implemention.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ModifyPwdController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AccountServiceImpl accountService;

    @PostMapping("/modifyPassword")
    @RequiresPermissions(value = { "user:modify" })
    public Object modifypwd(@RequestParam(name = "username", required = true) String username,
                            @RequestParam(name = "newPassword", required = true) String newPassword) {
        Account modPwd = new Account();
        modPwd.setAccountPhone(username);
        modPwd.setAccountPwd(newPassword);
        if (accountService.updatePassword(modPwd) != 0){
            BaseResponse<Object> ret = new BaseResponse<Object>();
            ret.setErrCode(0);
            ret.setMsg("修改成功！");
            return ret;
        }else{
            BaseResponse<Object> ret = new BaseResponse<Object>();
            ret.setErrCode(0);
            ret.setMsg("修改失败！");
            return ret;
        }

    }


}
