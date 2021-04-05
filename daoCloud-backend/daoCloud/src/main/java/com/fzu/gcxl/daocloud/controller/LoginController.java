package com.fzu.gcxl.daocloud.controller;

import com.fzu.gcxl.daocloud.config.JWTPackage.JwtUtils;
import com.fzu.gcxl.daocloud.entity.Account;
import com.fzu.gcxl.daocloud.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.service.implemention.AccountServiceImpl;
import com.fzu.gcxl.daocloud.service.implemention.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

// 登陆控制器
@Controller
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AccountServiceImpl accountService;

    @PostMapping(value = "/login")
    @ResponseBody
    public Object userLogin(@RequestParam(name = "username", required = true) String username,
                            @RequestParam(name = "password", required = true) String password, ServletResponse response) {

        // 获取当前用户主体
        Subject subject = SecurityUtils.getSubject();
        String msg = null;
        boolean loginSuccess = false;
        // 将用户名和密码封装成 UsernamePasswordToken 对象

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        System.out.println("进入login方法。。。");
        try {
            subject.login(token);
            msg = "登录成功。";
            loginSuccess = true;
        } catch (UnknownAccountException uae) { // 账号不存在
            msg = "用户名与密码不匹配，请检查后重新输入！";
        } catch (IncorrectCredentialsException ice) { // 账号与密码不匹配
            msg = "用户名与密码不匹配，请检查后重新输入！";
        } catch (LockedAccountException lae) { // 账号已被锁定
            msg = "该账户已被锁定，如需解锁请联系管理员！";
        } catch (AuthenticationException ae) { // 其他身份验证异常
            msg = "登录异常，请联系管理员！";
        }
        BaseResponse<Object> ret = new BaseResponse<Object>();
        if (loginSuccess) {
            // 若登录成功，签发 JWT token

            String jwtToken = JwtUtils.sign(username, JwtUtils.SECRET);
            System.out.println("登录成功，签发 JWT token"+jwtToken);
            // 将签发的 JWT token 设置到 HttpServletResponse 的 Header 中
            ((HttpServletResponse) response).setHeader(JwtUtils.AUTH_HEADER, jwtToken);
            //
            ret.setErrCode(0);
            ret.setMsg(msg);
            return ret;
        } else {
            ret.setErrCode(401);
            ret.setMsg(msg);
            return ret;
        }

    }
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "loginpage";
    }

    @GetMapping("/logout")
    public Object logout() {
        BaseResponse<Object> ret = new BaseResponse<Object>();
        ret.setErrCode(0);
        ret.setMsg("退出登录");
        return ret;
    }


}
