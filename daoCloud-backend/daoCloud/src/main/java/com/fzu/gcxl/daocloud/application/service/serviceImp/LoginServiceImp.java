package com.fzu.gcxl.daocloud.application.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.AccountService;
import com.fzu.gcxl.daocloud.application.service.LoginService;
import com.fzu.gcxl.daocloud.application.service.SmsService;
import com.fzu.gcxl.daocloud.application.service.UserService;
import com.fzu.gcxl.daocloud.application.service.assembler.UserDtoAssembler;
import com.fzu.gcxl.daocloud.domain.entity.Account;
import com.fzu.gcxl.daocloud.domain.entity.User;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.exception.CustomUnauthorizedException;
import com.fzu.gcxl.daocloud.domain.repository.AccountRepository;
import com.fzu.gcxl.daocloud.domain.repository.RoleRepository;
import com.fzu.gcxl.daocloud.domain.repository.UserRepository;
import com.fzu.gcxl.daocloud.infrastructure.util.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
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

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserDtoAssembler userDtoAssembler;


    @Override
    public BaseResponse login(@RequestBody JSONObject usertologin, HttpServletResponse httpServletResponse) {
        // 获取当前用户主体
        System.out.println("登陆的信息："+usertologin);
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
        String newPsd = new SimpleHash("MD5", password, salt, 3).toHex();
        if (newPsd.equals(account.getLoginPassword())) {
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            String token = JwtUtil.sign(username, currentTimeMillis);
            httpServletResponse.setHeader("Authorization", token);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");

            return new BaseResponse(HttpStatus.OK.value(), "登录成功(Login Success.)", userDtoAssembler.assembleUser(username));
        } else {
            return new BaseResponse(HttpStatus.OK.value(), "登录失败(Login Fail)", "LoginFailed");
        }
    }

    @Override
    public BaseResponse loginsms(@RequestBody JSONObject usertologin, HttpServletResponse httpServletResponse) {
        Subject subject = SecurityUtils.getSubject();
        String msg = null;
        boolean loginSuccess = false;
        String username = usertologin.getString("userphone");
        String mobile = usertologin.getString("mobiledevice");

        Account account = accountService.findAccountByPhone(username);
        if (account == null) {
            if (mobile.equals("MOBILEDEVICE")){
                if (smsService.verifySms(usertologin).getData().equals("success")){
                    Account newAccount = new Account();
                    User newUser = new User();

                    newAccount.setLoginPhone(username);
                    ByteSource salt = ByteSource.Util.bytes(username);
                    String newPsd = new SimpleHash("MD5", "123456", salt, 3).toHex();
                    newAccount.setLoginPassword(newPsd);
                    newUser.setUserPhone(username);

                    int res1 = userRepository.createNewUser(newUser);
                    int res2 = accountRepository.createNewAccount(newAccount);

                    String currentTimeMillis = String.valueOf(System.currentTimeMillis());
                    String token = JwtUtil.sign(username, currentTimeMillis);
                    httpServletResponse.setHeader("Authorization", token);
                    httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");

                    if (res1 == -1 || res2 == -1)
                        return new BaseResponse(HttpStatus.OK.value(), "快速注册失败", "FastSignupFailed");
                    return new BaseResponse(HttpStatus.OK.value(), "快速注册成功", "FastSignupSuccess");
                }else {
                    return smsService.verifySms(usertologin);
                }
            }
            if (mobile.equals("BACKEND")) {
                throw new CustomUnauthorizedException("该帐号不存在");
            }
        }

        if (smsService.verifySms(usertologin).getData().equals("success")) {
            String token = JwtUtil.sign(username, String.valueOf(System.currentTimeMillis()));
            System.out.println("loginsms:"+token);
            httpServletResponse.setHeader("Authorization", token);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
            return new BaseResponse(HttpStatus.OK.value(), "登录成功(Login Success.)", userDtoAssembler.assembleUser(username));
        } else {
            return new BaseResponse(HttpStatus.OK.value(), "登录失败(Login Fail)", "LoginFailed");
        }
    }
}
