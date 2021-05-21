package com.fzu.gcxl.daocloud.application.service.assembler;

import com.fzu.gcxl.daocloud.application.service.AccountService;
import com.fzu.gcxl.daocloud.domain.entity.User;
import com.fzu.gcxl.daocloud.frontinterface.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDtoAssembler {

    @Autowired
    private AccountService accountService;

    public UserDto assembleUser(String userphone){
        User userobj = accountService.findUserByPhone(userphone);
        UserDto userDto = new UserDto(userobj.getRoleId(),userobj.getUserName(),userobj.getUserPhone(),
                userobj.getUserSchool(),userobj.getUserSno() ,userobj.getUserDepartment());
        return userDto;
    }


}
