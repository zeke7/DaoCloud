package com.fzu.gcxl.daocloud.frontinterface.controller;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.AccountService;
import com.fzu.gcxl.daocloud.application.service.LoginService;
import com.fzu.gcxl.daocloud.domain.entity.Account;
import com.fzu.gcxl.daocloud.domain.exception.CustomUnauthorizedException;
import com.fzu.gcxl.daocloud.domain.repository.AccountRepository;
import com.fzu.gcxl.daocloud.domain.repository.UserRepository;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.infrastructure.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

// 登陆控制器
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @CrossOrigin
    @PostMapping(value = "/login")
    public BaseResponse userLogin(@RequestBody JSONObject usertologin, HttpServletResponse httpServletResponse) {
        // 将用户名和密码封装成 UsernamePasswordToken 对象
        return loginService.login(usertologin, httpServletResponse);
    }

    @CrossOrigin
    @PostMapping(value = "/loginbysms")
    public BaseResponse userLoginbySms(@RequestBody JSONObject usertologin, HttpServletResponse httpServletResponse) {
        return loginService.loginsms(usertologin, httpServletResponse);
    }

}
