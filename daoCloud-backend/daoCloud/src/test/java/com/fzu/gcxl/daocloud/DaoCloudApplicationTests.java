package com.fzu.gcxl.daocloud;

import com.fzu.gcxl.daocloud.application.service.SmsService;
import com.fzu.gcxl.daocloud.domain.repository.ClassInfoRepository;
import com.fzu.gcxl.daocloud.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.ws.Action;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class DaoCloudApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClassInfoRepository classInfoRepository;


    @Test
    void contextLoads() {
//        String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
//        System.out.println(new Date(System.currentTimeMillis()));

        System.out.println(classInfoRepository.selectUserByphonecode("cs110","110").getUserName());
    }


}
