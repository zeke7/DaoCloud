package com.fzu.gcxl.daocloud.controller;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.domain.entity.Account;
import com.fzu.gcxl.daocloud.domain.entity.User;
import com.fzu.gcxl.daocloud.domain.persistence.AccountRepository;
import com.fzu.gcxl.daocloud.domain.persistence.UserRepository;
import com.fzu.gcxl.daocloud.domain.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class SignupController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin
    @PostMapping("/signup")
    @ResponseBody
    public BaseResponse usersignup(@RequestBody JSONObject usersignup, HttpServletResponse httpServletResponse){

//        String userphone, String verifycode,String userpassword,
//                String username,String schoolname,String departmentname,
//                String userno, String userole, String accountperm

        String userphone = usersignup.getString("userphone");
        String password = usersignup.getString("password");
        String username = usersignup.getString("username");


        String userschoool = usersignup.getString("userschoool");
        String usersno = usersignup.getString("usersno");
        String userdepartment = usersignup.getString("userdepartment");
        String roleid = usersignup.getString("userole");


        Account accountInDatabase = accountRepository.findAccountByPhone(userphone);
        User userInDatabase = userRepository.findUserByName(userphone);
        boolean accountExist = true;
        boolean userExist = true;
        Account newAccount = new Account();
        User newUser = new User();


        // 如果手机号不重复
        if (accountInDatabase == null){
            newAccount.setLoginPhone(userphone);
            newAccount.setLoginPassword(password);
            accountExist = false;
        }

        // 用户名不重复
        if (userInDatabase == null){
            newUser.setUserName(username);
            newUser.setUserSchool(userschoool);
            newUser.setUserDepartment(userdepartment);
            newUser.setUserSno(usersno);
            newUser.setUserPhone(userphone);
            // userrole - 1=>student 2=>teacher
            if (roleid.equals("student")){
                newUser.setRoleId(2);
            }else if (roleid.equals("teacher")){
                newUser.setRoleId(1);
            }
            userExist = false;
        }

        if(accountExist){
            return new BaseResponse(HttpStatus.OK.value(), "账户已经存在", "");
        }else if (userExist){
            return new BaseResponse(HttpStatus.OK.value(), "用户名已经存在", "");
        }else{
            userRepository.createNewUser(newUser);
            accountRepository.createNewAccount(newAccount);
            return new BaseResponse(HttpStatus.OK.value(), "添加成功", "");
        }


    }

}
