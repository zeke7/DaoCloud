package com.fzu.gcxl.daocloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.util.JwtUtil;
import com.fzu.gcxl.daocloud.domain.entity.Account;
import com.fzu.gcxl.daocloud.domain.exception.CustomUnauthorizedException;
import com.fzu.gcxl.daocloud.domain.persistence.AccountRepository;
import com.fzu.gcxl.daocloud.domain.persistence.UserRepository;
import com.fzu.gcxl.daocloud.domain.response.BaseResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

// 登陆控制器
@Controller
public class LoginController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "loginpage";
    }


    @CrossOrigin
    @PostMapping(value = "/login")
    @ResponseBody
    public BaseResponse userLogin(@RequestBody JSONObject usertologin, HttpServletResponse httpServletResponse) {

        // 获取当前用户主体
        Subject subject = SecurityUtils.getSubject();
        String msg = null;
        boolean loginSuccess = false;
        // 将用户名和密码封装成 UsernamePasswordToken 对象

        String username = usertologin.getString("username");
        String password = usertologin.getString("password");

        System.out.printf(username);
        System.out.printf(password);

        Account account = accountRepository.findAccountByPhone(username);
        if (account == null) {
            throw new CustomUnauthorizedException("该帐号不存在");
        }
        ByteSource salt = ByteSource.Util.bytes(username);
//        String newPsd = new SimpleHash("MD5", password, salt, 3).toHex();
        if (password.equals(account.getLoginPassword())) {
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            String token = JwtUtil.sign(username, currentTimeMillis);
            httpServletResponse.setHeader("Authorization", token);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");

            return new BaseResponse(HttpStatus.OK.value(), "登录成功(Login Success.)", userRepository.findUserByPhone(username));
        } else {
            throw new CustomUnauthorizedException("帐号或密码错误");
        }

    }



}
