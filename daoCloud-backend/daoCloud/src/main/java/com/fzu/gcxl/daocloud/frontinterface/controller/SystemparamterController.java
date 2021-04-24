package com.fzu.gcxl.daocloud.frontinterface.controller;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.ClassService;
import com.fzu.gcxl.daocloud.application.service.SystemparametersService;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SystemparamterController {
    @Autowired
    SystemparametersService systemparametersService;

    @CrossOrigin
    @PostMapping("/systemparamters")
    public BaseResponse createSysParams(@RequestBody JSONObject SysParams){
        return systemparametersService.createNewSysParams(SysParams);
    }

    @CrossOrigin
    @GetMapping("/systemparamters")
    public BaseResponse getSysParams(String sysname){
        return systemparametersService.getSysParamsByName(sysname);
    }

    @CrossOrigin
    @DeleteMapping("/systemparamters")
    public BaseResponse delSysParams(Integer sid){
        return systemparametersService.delSysParamsById(sid);
    }

    @CrossOrigin
    @PutMapping("/systemparamters")
    public BaseResponse updateSysParams(@RequestBody JSONObject SysParams){
        return systemparametersService.updateSysParamsByCode(SysParams);
    }

    @CrossOrigin
    @GetMapping("/allsystemparamters")
    public BaseResponse getAllSysParams(){
        return systemparametersService.getAllSysParams();
    }


}
