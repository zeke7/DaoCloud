package com.fzu.gcxl.daocloud.frontinterface.controller;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.ClassService;
import com.fzu.gcxl.daocloud.domain.entity.Class;
import com.fzu.gcxl.daocloud.domain.repository.ClassRepository;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    @GetMapping("/codeClasses")
    public BaseResponse getClassByCode(String classcode){
        return classService.getClassesByCode(classcode);
    }

    @CrossOrigin
    @PutMapping("/inclasses")
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
    public BaseResponse getallClassesDto(String userphone){
        System.out.println(userphone);
        return classService.selectAllClassesInfo(userphone);
    }




}
