package com.fzu.gcxl.daocloud.controller;

import com.fzu.gcxl.daocloud.entity.User;
import com.fzu.gcxl.daocloud.mapper.AccountMapper;
import com.fzu.gcxl.daocloud.mapper.UserMapper;
import com.fzu.gcxl.daocloud.service.AccountService;
import com.fzu.gcxl.daocloud.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// 登陆控制器
@Controller
public class LoginController {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    UserMapper userMapper;

    @ResponseBody
    @RequestMapping("/accountest")
    public String accountest(Model model){

        User user = userMapper.queryUserByPhone("12221212");

        return user.getUsername();
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "loginpage";
    }

    @RequestMapping("/login")
    public String login(String username, String password, Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try{
            subject.login(token); //执行登陆的方法
            return "index";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名错误");
            return "loginpage";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "loginpage";
        }
    }

    @ResponseBody
    @RequestMapping("/noauth")
    public String unauthorized(){
        return "xxxxxxxxxxxxxxxx";
    }

}
