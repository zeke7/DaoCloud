package com.fzu.gcxl.daocloud.application.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.AccountService;
import com.fzu.gcxl.daocloud.application.service.SmsService;
import com.fzu.gcxl.daocloud.domain.entity.Account;
import com.fzu.gcxl.daocloud.domain.entity.User;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.exception.UserFriendException;
import com.fzu.gcxl.daocloud.domain.repository.AccountRepository;
import com.fzu.gcxl.daocloud.domain.repository.UserRepository;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

        String userschoool = usersignup.getString("userschool");
        String usersno = usersignup.getString("usersno");
        String userdepartment = usersignup.getString("userdepartment");
        String roleid = usersignup.getString("userole");

        String mobile = usersignup.getString("mobiledevice");

        System.out.println(usersignup);
        System.out.println("注册");

        if (mobile.equals("MOBILEDEVICE")) {

            Account accountInDatabase = accountRepository.findAccountByPhone(userphone);
            User userInDatabase = userRepository.findUserByName(userphone);
            boolean accountExist = true;
            boolean userExist = true;
            Account newAccount = new Account();
            User newUser = new User();


            // 如果手机号不重复
            if (accountInDatabase == null){
                newAccount.setLoginPhone(userphone);
                ByteSource salt = ByteSource.Util.bytes(userphone);
                String newPsd = new SimpleHash("MD5", password, salt, 3).toHex();
                newAccount.setLoginPassword(newPsd);
                System.out.println(newPsd);
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
                    newUser.setRoleId(3);
                }else if (roleid.equals("teacher")){
                    newUser.setRoleId(2);
                }else if(roleid.equals("admin")){
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

        if(mobile.equals("BACKEND"))
        {
            if (smsService.verifySms(usersignup).getData().equals("success")){
                Account accountInDatabase = accountRepository.findAccountByPhone(userphone);
                User userInDatabase = userRepository.findUserByName(userphone);
                boolean accountExist = true;
                boolean userExist = true;
                Account newAccount = new Account();
                User newUser = new User();


                // 如果手机号不重复
                if (accountInDatabase == null){
                    newAccount.setLoginPhone(userphone);
                    ByteSource salt = ByteSource.Util.bytes(userphone);
                    String newPsd = new SimpleHash("MD5", password, salt, 3).toHex();
                    newAccount.setLoginPassword(newPsd);
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
                        newUser.setRoleId(3);
                    }else if (roleid.equals("teacher")){
                        newUser.setRoleId(2);
                    }else if(roleid.equals("admin")){
                        newUser.setRoleId(1);
                    }
                    userExist = false;
                }

                if(accountExist){
                    return new BaseResponse(HttpStatus.OK.value(), "账户已经存在", "");
                }else if (userExist){
                    return new BaseResponse(HttpStatus.OK.value(), "用户名已经存在", "");
                }else{
                    System.out.println();
                    userRepository.createNewUser(newUser);
                    accountRepository.createNewAccount(newAccount);
                    return new BaseResponse(HttpStatus.OK.value(), "添加成功", "");
                }
            }else {
                return new BaseResponse(HttpStatus.OK.value(), "验证码错误", "");
            }
        }
        return new BaseResponse(HttpStatus.OK.value(), "注册失败", "");

    }

    public BaseResponse resetPassword(JSONObject usertomodfiypwd){
        String username = usertomodfiypwd.getString("username");
        String oldPassword = usertomodfiypwd.getString("password");
        ByteSource salt = ByteSource.Util.bytes(username);
        String oldPasswordhex = new SimpleHash("MD5", oldPassword, salt, 3).toHex();
        String newPassword = usertomodfiypwd.getString("newpassword");

        if (oldPasswordhex.equals(accountRepository.findAccountByPhone(username).getLoginPassword())){
            Account modPwd = new Account();
            modPwd.setLoginPhone(username);
//            ByteSource salt1 = ByteSource.Util.bytes(username);
            String newPsd = new SimpleHash("MD5", newPassword, salt, 3).toHex();
            modPwd.setLoginPassword(newPsd);

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
        String username = usertomodfiypwd.getString("userphone");

        String newPassword = usertomodfiypwd.getString("newpassword");
        Account account = accountService.findAccountByPhone(username);

        if (account == null) {
            throw new UserFriendException("该帐号不存在");
        }
        boolean issuccess = smsService.verifySms(usertomodfiypwd).getData().equals("success");
        if (issuccess){
            Account modPwd = new Account();
            modPwd.setLoginPhone(username);
            ByteSource salt = ByteSource.Util.bytes(username);
            String newPsd = new SimpleHash("MD5", newPassword, salt, 3).toHex();
            modPwd.setLoginPassword(newPsd);
            int res = accountRepository.updateAccountPassword(modPwd);
            if (res != -1){
                return new BaseResponse(HttpStatus.OK.value(), "密码修改成功", "ModifyPasswordSuccess");
            }else{
                return new BaseResponse(HttpStatus.OK.value(), "密码找回失败", "ModifyPasswordFailed");
            }
        }else {
            return new BaseResponse(HttpStatus.OK.value(), "验证码不正确", "ModifyPasswordFailed");
        }


    }
}
