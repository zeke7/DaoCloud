package com.fzu.gcxl.daocloud.controller;

import com.fzu.gcxl.daocloud.domain.entity.Class;
import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.domain.persistence.ClassRepository;
import com.fzu.gcxl.daocloud.domain.response.BaseResponse;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClassController {

    @Autowired
    ClassRepository classRepository;

    @CrossOrigin
    @GetMapping("/createClass")
    public BaseResponse addClass(@RequestBody JSONObject createClass){
        String classname = createClass.getString("classname");
        String userPhone = createClass.getString("userPhone");
        Integer classmember = createClass.getInteger("classmembers");
        String classno = createClass.getString("classno");

        Class newClass = new Class();
        newClass.setClassName(classname);
        newClass.setUserPhone(userPhone);
        newClass.setClassMember(classmember);
        newClass.setClassNum(classno);

        if (classRepository.findClassByName(classname) == null && classRepository.findClassByNum(classno) == null){
            int success = classRepository.createNewClass(newClass);
            if(success != 0){
                return new BaseResponse(HttpStatus.OK.value(), "班课创建成功", "CreateClassSuccess");
            }else{
                return new BaseResponse(HttpStatus.OK.value(), "班课创建失败", "CreateClassFail");
            }
        }else {
            return new BaseResponse(HttpStatus.OK.value(), "班课创建失败, 班课名称或班课编号重复", "CreateClassFail_Num_Name");
        }

    }

    @CrossOrigin
    @GetMapping("/getClasses")
    public BaseResponse getClassList(@RequestBody JSONObject getClass){
        String userPhone = getClass.getString("userphone");
        List<Class> classes = classRepository.getClassesByUser(userPhone);
        System.out.println(classes);

        return new BaseResponse(HttpStatus.OK.value(), "获取班课列表", classes);
    }

}
