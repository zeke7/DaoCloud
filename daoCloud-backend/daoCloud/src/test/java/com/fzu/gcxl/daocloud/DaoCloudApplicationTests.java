package com.fzu.gcxl.daocloud;

import com.fzu.gcxl.daocloud.entity.User;
import com.fzu.gcxl.daocloud.mapper.AccountMapper;
import com.fzu.gcxl.daocloud.mapper.UserMapper;
import com.fzu.gcxl.daocloud.service.AccountService;
import com.fzu.gcxl.daocloud.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DaoCloudApplicationTests {



    @Autowired
    AccountService accountService;


    @Test
    void contextLoads() {

        System.out.println(accountService.queryAccountByPhone("18039078699"));
    }

}
