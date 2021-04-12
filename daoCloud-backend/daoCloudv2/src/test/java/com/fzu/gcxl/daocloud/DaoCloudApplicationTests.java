package com.fzu.gcxl.daocloud;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.fzu.gcxl.daocloud.domain.entity.Class;
import com.fzu.gcxl.daocloud.domain.persistence.AccountRepository;
import com.fzu.gcxl.daocloud.domain.persistence.ClassRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DaoCloudApplicationTests {

    @Autowired
    ClassRepository classRepository;

    @Test
    void contextLoads() {
//        List<Class> classlist = classRepository.getClassesByUser("131");
//        System.out.println(classlist);

        Class newClass = new Class();
        newClass.setClassName("classname");
        newClass.setUserPhone("userPhone");
        newClass.setClassMember(500);
        newClass.setClassNum("classno");
        System.out.println(newClass);
        classRepository.createNewClass(newClass);
 //       classRepository.getClassesByUser("131");

    }

}
