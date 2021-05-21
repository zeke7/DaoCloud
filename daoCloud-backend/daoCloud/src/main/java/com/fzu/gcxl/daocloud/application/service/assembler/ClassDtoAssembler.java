package com.fzu.gcxl.daocloud.application.service.assembler;

import com.fzu.gcxl.daocloud.domain.entity.Class;
import com.fzu.gcxl.daocloud.domain.entity.User;
import com.fzu.gcxl.daocloud.domain.repository.ClassRepository;
import com.fzu.gcxl.daocloud.domain.repository.UserRepository;
import com.fzu.gcxl.daocloud.frontinterface.dto.ClassInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassDtoAssembler {
    @Autowired
    ClassRepository classRepository;

    @Autowired
    UserRepository userRepository;

    public ClassInfoDto classinfoAssembler(String userphone){

        ClassInfoDto classInfoDTO = new ClassInfoDto();

        String userName = userRepository.findUserByPhone(userphone).getUserName();
        String userSchool = userRepository.findUserByPhone(userphone).getUserSchool();
        String userDepartment = userRepository.findUserByPhone(userphone).getUserDepartment();
        List<Class> classes = classRepository.selectAllClassesByUser(userphone);

        classInfoDTO.setUserName(userName);
        classInfoDTO.setUserSchool(userSchool);
        classInfoDTO.setUserDepartment(userDepartment);
        classInfoDTO.setClasses(classes);

        return classInfoDTO;
    }

    public List<ClassInfoDto> allClassinfoAssembler(){

        List<ClassInfoDto> allclassInfo = new ArrayList<>();
        List<Class> classes1 = classRepository.selectAllClasses();
        List<String> userlist = new ArrayList<>();

        for (Class c: classes1) {
            String userphone = c.getUserPhone();
            System.out.println(userphone);
            if (!userlist.contains(userphone)){
                User userinfo = userRepository.findUserByPhone(userphone);
                List<Class> classes = classRepository.selectAllClassesByUser(userphone);
                ClassInfoDto classInfoDTO = new ClassInfoDto();
                if (userinfo != null){
                    classInfoDTO.setUserName(userinfo.getUserName());
                    classInfoDTO.setUserSchool(userinfo.getUserSchool());
                    classInfoDTO.setUserDepartment(userinfo.getUserDepartment());
                }
                classInfoDTO.setClasses(classes);
                System.out.println(classes);
                System.out.println(classInfoDTO);
                allclassInfo.add(classInfoDTO);
                userlist.add(userphone);
            }
        }

        return allclassInfo;
    }
}
