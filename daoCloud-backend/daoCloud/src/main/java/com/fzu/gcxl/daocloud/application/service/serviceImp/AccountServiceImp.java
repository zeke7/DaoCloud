package com.fzu.gcxl.daocloud.application.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.AccountService;
import com.fzu.gcxl.daocloud.application.service.SmsService;
import com.fzu.gcxl.daocloud.domain.entity.Account;
import com.fzu.gcxl.daocloud.domain.entity.User;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.exception.CustomException;
import com.fzu.gcxl.daocloud.domain.exception.CustomUnauthorizedException;
import com.fzu.gcxl.daocloud.domain.repository.AccountRepository;
import com.fzu.gcxl.daocloud.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AccountServiceImp implements AccountService {


    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SmsService smsService;

    public Account findAccountByPhone(String username){
        return accountRepository.findAccountByPhone(username);
    }

    public User findUserByPhone(String username){
        return userRepository.findUserByPhone(username);
    }

    public int updateAccountPassword(Account account){
        return accountRepository.updateAccountPassword(account);
    }

    public User findUserByName(String userphone){
        return userRepository.findUserByName(userphone);
    }

    public BaseResponse createNewAccount(JSONObject usersignup){

        String userphone = usersignup.getString("userphone");
        String password = usersignup.getString("password");
        String username = usersignup.getString("username");

        String userschoool = usersignup.getString("userschoool");
        String usersno = usersignup.getString("usersno");
        String userdepartment = usersignup.getString("userdepartment");
        String roleid = usersignup.getString("userole");

        String mobile = usersignup.getString("mobiledevice");

        if (smsService.verifySms(usersignup).getData().equals("success") || mobile.equals("MOBILEDEVICE")) {

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
        }else {
            return new BaseResponse(HttpStatus.OK.value(), "验证码错误", "");
        }
    }

    public BaseResponse resetPassword(JSONObject usertomodfiypwd){
        String username = usertomodfiypwd.getString("username");
        String oldPassword = usertomodfiypwd.getString("password");
        String newPassword = usertomodfiypwd.getString("newpassword");

        if (oldPassword.equals(accountRepository.findAccountByPhone(username).getLoginPassword())){
            Account modPwd = new Account();
            modPwd.setLoginPhone(username);
            modPwd.setLoginPassword(newPassword);

            if (accountRepository.updateAccountPassword(modPwd) != 0){
                return new BaseResponse(HttpStatus.OK.value(), "密码修改成功", "ModifyPasswordSuccess");
            }else{
                return new BaseResponse(HttpStatus.OK.value(), "密码修改失败", "ModifyPasswordFail");
            }
        }else {
            return new BaseResponse(HttpStatus.OK.value(), "原密码不正确，密码修改失败", "ModifyPasswordFail");
        }


    }

    public BaseResponse backPassword(JSONObject usertomodfiypwd){
        String username = usertomodfiypwd.getString("username");
        String newPassword = usertomodfiypwd.getString("newpassword");
        Account account = accountService.findAccountByPhone(username);

        if (account == null) {
            throw new CustomException("该帐号不存在");
        }

        if (smsService.verifySms(usertomodfiypwd).getData().equals("success")){
            Account modPwd = new Account();
            modPwd.setLoginPhone(username);
            modPwd.setLoginPassword(newPassword);

            if (accountRepository.updateAccountPassword(modPwd) != 0){
                return new BaseResponse(HttpStatus.OK.value(), "密码修改成功", "ModifyPasswordSuccess");
            }else{
                return new BaseResponse(HttpStatus.OK.value(), "密码找回失败", "ModifyPasswordFailed");
            }
        }else {
            return new BaseResponse(HttpStatus.OK.value(), "验证码不正确", "ModifyPasswordFailed");
        }


    }
}
