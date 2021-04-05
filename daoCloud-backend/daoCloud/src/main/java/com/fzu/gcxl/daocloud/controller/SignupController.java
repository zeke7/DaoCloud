package com.fzu.gcxl.daocloud.controller;

import com.fzu.gcxl.daocloud.entity.Account;
import com.fzu.gcxl.daocloud.entity.User;
import com.fzu.gcxl.daocloud.service.implemention.AccountServiceImpl;
import com.fzu.gcxl.daocloud.service.implemention.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AccountServiceImpl accountService;

//    @GetMapping ("/toSignup")
//    public String signup2(){
//        return "signup/signupage";
//    }

    @PostMapping("/signup")
    public String signup1(String userphone, String verifycode,String userpassword,
                           String username,String schoolname,String departmentname,
                          String userno, String userole, String accountperm){

        Account accountInDatabase = accountService.queryAccountByPhone(userphone);
        User userInDatabase = userService.queryUserByName(username);
        boolean accountExist = true;
        boolean userExist = true;
        Account newAccount = new Account();
        User newUser = new User();


        // 如果手机号不重复
        if (accountInDatabase == null){
            newAccount.setAccountPhone(userphone);
            newAccount.setAccountPwd(userpassword);
            newAccount.setAccountVcode(verifycode);
            newAccount.setAccountRole("user");
            newAccount.setAccountPerm(accountperm);
            accountExist = false;
        }

        // 用户名不重复
        if (userInDatabase == null){
            newUser.setUsername(username);
            newUser.setUserSchool(schoolname);
            newUser.setUserDepartment(departmentname);
            newUser.setUserNo(userno);
            newUser.setUserPhone(userphone);
            // userrole - 1=>student 2=>teacher
            newUser.setUserRole(userole);
            userExist = false;
        }

        if(accountExist){
            return "用户已存在";
        }else if (userExist){
            return "用户名已存在";
        }else{
            userService.insertUser(newUser);
            accountService.addAccount(newAccount);
            return "添加成功";
        }


    }

}
