package com.fzu.gcxl.daocloud.controller;

import com.fzu.gcxl.daocloud.entity.Account;
import com.fzu.gcxl.daocloud.entity.User;
import com.fzu.gcxl.daocloud.mapper.AccountMapper;
import com.fzu.gcxl.daocloud.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignupController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccountMapper accountMapper;

    @GetMapping ("/toSignup")
    public String signup2(){
        return "signupage";
    }

    @PostMapping("/signup")
    public String signup1(String usernickname, String userphone, String userpassword,
                          String verifycode, String schoolname, String username,
                          String userno, String departmentname, String userdisp, String userole){

        Account newAccount = new Account();
        newAccount.setLoginPhone(userphone);
        newAccount.setLoginPassword(userpassword);
        newAccount.setLoginVerifyCode(verifycode);

        User newUser = new User();
        newUser.setUserNickname(usernickname);
        newUser.setUserSchool(schoolname);
        newUser.setUsername(username);
        newUser.setUserNo(userno);
        newUser.setUserDepartment(departmentname);
        newUser.setUserDisp(userdisp);
        newUser.setUserPhone(userphone);
        // userrole - 1=>student 2=>teacher
        newUser.setUserRole(userole);

        System.out.println(newAccount);

        accountMapper.addAccount(newAccount);
        userMapper.addUser(newUser);


        return "userinfo";
    }

}
