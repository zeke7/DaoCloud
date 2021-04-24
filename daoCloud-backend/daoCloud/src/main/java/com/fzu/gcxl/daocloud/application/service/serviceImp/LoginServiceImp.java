package com.fzu.gcxl.daocloud.application.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.AccountService;
import com.fzu.gcxl.daocloud.application.service.LoginService;
import com.fzu.gcxl.daocloud.application.service.SmsService;
import com.fzu.gcxl.daocloud.domain.entity.Account;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.exception.CustomUnauthorizedException;
import com.fzu.gcxl.daocloud.infrastructure.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;

@Service
public class LoginServiceImp implements LoginService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SmsService smsService;

    @Override
    public BaseResponse login(@RequestBody JSONObject usertologin, HttpServletResponse httpServletResponse) {
        // 获取当前用户主体
        Subject subject = SecurityUtils.getSubject();
        String msg = null;
        boolean loginSuccess = false;
        String username = usertologin.getString("username");
        String password = usertologin.getString("password");
        Account account = accountService.findAccountByPhone(username);
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

            return new BaseResponse(HttpStatus.OK.value(), "登录成功(Login Success.)", accountService.findUserByPhone(username));
        } else {
            return new BaseResponse(HttpStatus.OK.value(), "登录失败(Login Fail)", "LoginFailed");
        }
    }

    @Override
    public BaseResponse loginsms(@RequestBody JSONObject usertologin, HttpServletResponse httpServletResponse) {

        String username = usertologin.getString("username");

        Account account = accountService.findAccountByPhone(username);
        if (account == null) {
            throw new CustomUnauthorizedException("该帐号不存在");
        }

        if (smsService.verifySms(usertologin).getData().equals("success")) {
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            String token = JwtUtil.sign(username, currentTimeMillis);
            httpServletResponse.setHeader("Authorization", token);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");

            return new BaseResponse(HttpStatus.OK.value(), "登录成功(Login Success.)", accountService.findUserByPhone(username));
        } else {
            return new BaseResponse(HttpStatus.OK.value(), "登录失败(Login Fail)", "LoginFailed");
        }
    }
}
