package com.fzu.gcxl.daocloud.frontinterface.controller;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.ClassService;
import com.fzu.gcxl.daocloud.domain.entity.Class;
import com.fzu.gcxl.daocloud.domain.repository.ClassRepository;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequiresRoles("teacher")
public class ClassController {

    @Autowired
    ClassService classService;

    @CrossOrigin
    @PostMapping("/classes")
    public BaseResponse createClass(@RequestBody JSONObject createClass){
        return classService.createNewClass(createClass);
    }

    @CrossOrigin
    @GetMapping("/classes")
    public BaseResponse getClassByUser(String userPhone){
        return classService.getClassesByUser(userPhone);
    }

    @CrossOrigin
    @DeleteMapping("/classes")
    public BaseResponse delClass(String classCode){
        return classService.delClassByCode(classCode);
    }

    @CrossOrigin
    @PutMapping("/classes")
    public BaseResponse updateClass(@RequestBody JSONObject createClass){
        return classService.updateClassByCode(createClass);
    }

    @CrossOrigin
    @PutMapping("/classclousres")
    public BaseResponse closeClass(@RequestBody JSONObject closeClass){
        return classService.closeClassByCode(closeClass);
    }

    @CrossOrigin
    @PutMapping("/classallowed")
    public BaseResponse allowClass(@RequestBody JSONObject closeClass){
        return classService.ClassNotAllowedByCode(closeClass);
    }

    @CrossOrigin
    @GetMapping("/codeClasses")
    public BaseResponse getClassByCode(String classCode){
        return classService.getClassesByCode(classCode);
    }

    @CrossOrigin
    @PostMapping("/inclasses")
    public BaseResponse classinfo(@RequestBody JSONObject closeClass){
        return classService.joinClassByCode(closeClass);
    }

    @CrossOrigin
    @GetMapping("/allclasses")
    public BaseResponse getallClasses(){
        return classService.selectAllClasses();
    }

    @CrossOrigin
    @GetMapping("/classesdto")
    public BaseResponse getClassesDto(String userphone){
        return classService.selectAllClassesInfo(userphone);
    }

    @CrossOrigin
    @GetMapping("/classinfodto")
    public BaseResponse getClassinfoDto(String userphone){
        return classService.selectAllClassesInfoPerson(userphone);
    }

    @CrossOrigin
    @GetMapping("/allclassesdto")
    public BaseResponse getallClassesDto(){
        return classService.selectAllInfo();
    }

    @CrossOrigin
    @GetMapping("/students")
    public BaseResponse getStuNums(String classCode){
        return classService.getStudentCountsByClassCode(classCode);
    }

//    @CrossOrigin
//    @GetMapping("/classesdtobynums")
//    public BaseResponse getallClassesDto(String userphone, Integer counts){
//        return classService.selectAllClassesInfo(userphone);
//    }





}
