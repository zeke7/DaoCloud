package com.fzu.gcxl.daocloud.frontinterface.controller;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.DictionaryService;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
//@RequiresRoles("admin")
public class DictionaryController {

    @Autowired
    DictionaryService dictionaryService;

    @PostMapping(path = "/dictionaries")
    public BaseResponse add(@RequestBody JSONObject dictionary){
        return dictionaryService.createNewDictionary(dictionary);
    }

    @DeleteMapping(path = "/dictionaries")
    public BaseResponse del(String code){
        return dictionaryService.deleteDictionaryByCode(code);
    }

    @PutMapping(path = "/dictionaries")
    public BaseResponse edit(@RequestBody JSONObject dictionary){
        return dictionaryService.updateDictionary(dictionary);
    }

    @GetMapping(path = "/dictionaries")
    public BaseResponse find(String code){
        return dictionaryService.selectDictionaryByCode(code);
    }

    @PostMapping(path = "/dictionarydetails")
    public BaseResponse adddetails(@RequestBody JSONObject dictionary){
        return dictionaryService.createNewDictionaryDetail(dictionary);
    }

    @DeleteMapping(path = "/dictionarydetails")
    public BaseResponse deldetails(String detailcode){
        return dictionaryService.deleteDictionaryDetailsByCode(detailcode);
    }


    @PutMapping(path = "/dictionarydetails")
    public BaseResponse editdetails(@RequestBody JSONObject dictionary){
        return dictionaryService.updateDictionaryDetails(dictionary);
    }

    @GetMapping(path = "/dictionarydetails")
    public BaseResponse finddetails(String detailcode){
        return dictionaryService.selectDictionaryDetailsByCode(detailcode);
    }

    @GetMapping(path = "/alldictionaries")
    public BaseResponse getalldics(){
        return dictionaryService.selectAllDictionaries();
    }

    @GetMapping(path = "/alldictionarydetails")
    public BaseResponse getalldicdetails(String diccode){
        return dictionaryService.selectAllDictionaryDetails(diccode);
    }




}
