package com.fzu.gcxl.daocloud.application.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.UserService;
import com.fzu.gcxl.daocloud.domain.entity.Account;
import com.fzu.gcxl.daocloud.domain.entity.ClassInfo;
import com.fzu.gcxl.daocloud.domain.entity.User;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.exception.UserFriendException;
import com.fzu.gcxl.daocloud.domain.repository.AccountRepository;
import com.fzu.gcxl.daocloud.domain.repository.ClassInfoRepository;
import com.fzu.gcxl.daocloud.domain.repository.UserRepository;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.jacoco.agent.rt.internal_f3994fa.core.internal.flow.IFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClassInfoRepository classInfoRepository;


    @Override
    public User findUserByPhone(String userphone) {
        return userRepository.findUserByPhone(userphone);
    }

    @Override
    public BaseResponse createNewUser(JSONObject userInfo) {

        try {
            String userphone = userInfo.getString("userphone");
            String password = userInfo.getString("password");

            String username = userInfo.getString("username");
            String userschoool = userInfo.getString("userschool");
            String usersno = userInfo.getString("usersno");
            String userdepartment = userInfo.getString("userdepartment");
            String roleid = userInfo.getString("userole");

            Account accountInDatabase = accountRepository.findAccountByPhone(userphone);
            User userInDatabase = userRepository.findUserByName(userphone);
            boolean accountExist = true;
            boolean userExist = true;
            Account newAccount = new Account();
            User newUser = new User();


            // 如果手机号不重复
            if (accountInDatabase == null) {
                newAccount.setLoginPhone(userphone);
                ByteSource salt = ByteSource.Util.bytes(userphone);
                String newPsd = new SimpleHash("MD5", password, salt, 3).toHex();
                newAccount.setLoginPassword(newPsd);
                accountExist = false;
            }

            // 用户名不重复
            if (userInDatabase == null) {
                newUser.setUserName(username);
                newUser.setUserSchool(userschoool);
                newUser.setUserDepartment(userdepartment);
                newUser.setUserSno(usersno);
                newUser.setUserPhone(userphone);
                // userrole - 1=>student 2=>teacher
                if (roleid.equals("student")) {
                    newUser.setRoleId(3);
                } else if (roleid.equals("teacher")) {
                    newUser.setRoleId(2);
                } else if (roleid.equals("admin")) {
                    newUser.setRoleId(1);
                }
                userExist = false;
            }

            if (accountExist) {
                return new BaseResponse(HttpStatus.OK.value(), "账户已经存在", "");
            } else if (userExist) {
                return new BaseResponse(HttpStatus.OK.value(), "用户名已经存在", "");
            } else {
                userRepository.createNewUser(newUser);
                accountRepository.createNewAccount(newAccount);
                return new BaseResponse(HttpStatus.OK.value(), "添加成功", "");
            }
        }catch (Exception e){
            throw new UserFriendException("用户添加失败");
        }

    }

    @Override
    public BaseResponse deleteUser(String userphone) {
        Account accountByPhone = accountRepository.findAccountByPhone(userphone);
        User userByPhone = userRepository.findUserByPhone(userphone);
        if (accountByPhone == null && userByPhone == null)
            return new BaseResponse(200, "账户不存在", "");
        if (accountByPhone != null){
            int res = accountRepository.deleteByPrimaryKey(accountByPhone.getAccountId());
            if (res == -1)
                return new BaseResponse(500, "删除账户失败", "");
        }
        if (userByPhone != null){
            int res = userRepository.deleteByPrimaryKey(userByPhone.getUserId());
            if (res == -1)
                return new BaseResponse(500, "删除账户信息失败", "");
        }
        return new BaseResponse(200, "删除账户成功", "");
    }

    public BaseResponse updateUser(JSONObject userInfo) {
//        userRepository.updateByPrimaryKeySelective()
        // 用户昵称

        String userphone = userInfo.getString("userphone");
        String username = userInfo.getString("username");
        String userschoool = userInfo.getString("userschool");
        String usersno = userInfo.getString("usersno");
        String userdepartment = userInfo.getString("userdepartment");

        User userByPhone = userRepository.findUserByPhone(userphone);
        if (userByPhone == null){
            return new BaseResponse(500, "用户信息更新失败", "用户不存在");
        }

        Integer userid = userByPhone.getUserId();

        User userInfonew = new User();

        userInfonew.setUserId(userid);
        userInfonew.setUserName(username);
        userInfonew.setUserSchool(userschoool);
        userInfonew.setUserDepartment(userdepartment);
        userInfonew.setUserSno(usersno);
        userInfonew.setUserPhone(userphone);

        int res = userRepository.updateByPrimaryKeySelective(userInfonew);
        if (res == -1)
            return new BaseResponse(HttpStatus.OK.value(), "用户信息更新失败", "UpdateFailed");

        return new BaseResponse(200, "用户信息更新成功", "");
    }

    @Override
    public BaseResponse updateUserInfo(JSONObject userInfo) {
//        userRepository.updateByPrimaryKeySelective()
        // 用户昵称
        String userid = userInfo.getString("userPhone");
        String username = userInfo.getString("userName");
        String userschool = userInfo.getString("userSchool");
        String userdept = userInfo.getString("userDept");
        String userno = userInfo.getString("userSno");

        User userInfonew = new User();

        if (username != null){
            if (userRepository.findUserByName(username) == null)
                userInfonew.setUserName(username);
            else
                return new BaseResponse(500, "用户名已存在", "");
        }

        userInfonew.setUserSchool(userschool);
        userInfonew.setUserDepartment(userdept);
        userInfonew.setUserSno(userno);
        userInfonew.setUserPhone(userid);

        int res = userRepository.updateByPhoneSelective(userInfonew);
        if (res == -1)
            return new BaseResponse(HttpStatus.OK.value(), "用户信息更新失败", "UpdateFailed");

        return new BaseResponse(200, "用户信息更新成功", "");
    }

    @Override
    public BaseResponse getInfoByRoleId(Integer roleId) {
        try{
            return new BaseResponse(500, "根据用户角色ID获取用户信息列表", userRepository.selectUserByRoleId(roleId));
        }catch (Exception e){
            throw new UserFriendException("根据用户角色ID获取用户信息列表出错");
        }
    }

    @Override
    public BaseResponse getStudentInfoByClass(String classCode) {
        List<ClassInfo> classInfoList = classInfoRepository.selectAllClassInfoByClassCode(classCode);
        List<User> UserList = new ArrayList<>();
        if (classInfoList != null){
            for (ClassInfo cinfo: classInfoList) {
                if (cinfo.getUserPhone() != null){
                    User userByPhone = userRepository.findUserByPhone(cinfo.getUserPhone());
                    UserList.add(userByPhone);
                }
            }
        }
        return new BaseResponse(500, "根据用户角色ID获取用户信息列表", UserList);
    }

    @Override
    public BaseResponse getAllUser() {
        List<User> userList = userRepository.selectAllUser();
        return new BaseResponse(500, "获取用户信息列表", userList);
    }


}
