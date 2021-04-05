package com.fzu.gcxl.daocloud;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fzu.gcxl.daocloud.entity.Account;
import com.fzu.gcxl.daocloud.entity.User;
import com.fzu.gcxl.daocloud.mapper.AccountMapper;
import com.fzu.gcxl.daocloud.mapper.UserMapper;
import com.fzu.gcxl.daocloud.service.AccountService;
import com.fzu.gcxl.daocloud.service.UserService;
import com.fzu.gcxl.daocloud.service.implemention.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DaoCloudApplicationTests {

    @Autowired
    AccountServiceImpl accountService;

    @Test
    void contextLoads() {
//        DefaultProfile profile = DefaultProfile.getProfile("cn-qingdao", "", "<accessSecret>");
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
//        request.setSysMethod(MethodType.POST);
//        request.setSysDomain("dysmsapi.aliyuncs.com");
//        request.setSysVersion("2017-05-25");
//        request.setSysAction("SendSms");
//        request.putQueryParameter("PhoneNumbers", "");
//        request.putQueryParameter("SignName", "");
//        request.putQueryParameter("TemplateCode", "");
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//        } catch (ServerException e) {
//            e.printStackTrace();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
        Account account = new Account();
        account.setAccountPwd("ni12wo34");
        account.setAccountPhone("180");
        accountService.updatePassword(account);



    }

}
