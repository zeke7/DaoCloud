package com.fzu.gcxl.daocloud.application.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.SystemparametersService;
import com.fzu.gcxl.daocloud.domain.entity.SystemParameter;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.repository.SystemParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemparametersServiceImp implements SystemparametersService {
    @Autowired
    SystemParameterRepository systemParameterRepository;

    public BaseResponse createNewSysParams(JSONObject sysparameter){
        String sysname = sysparameter.getString("sysname");
        String sysvalue = sysparameter.getString("sysvalue");

        SystemParameter systemParameter = new SystemParameter();
        systemParameter.setSysName(sysname);
        systemParameter.setSysParameter(sysvalue);


        if (systemParameterRepository.selectBysname(sysname) == null){
            int success = systemParameterRepository.insert(systemParameter);
            if(success != 0){
                return new BaseResponse(HttpStatus.OK.value(), "系统参数创建成功", "Success");
            }else{
                return new BaseResponse(HttpStatus.OK.value(), "系统参数创建失败", "Fail");
            }
        }else {
            return new BaseResponse(HttpStatus.OK.value(), "系统参数已存在", "Exist");
        }
    }

    public BaseResponse getSysParamsByName(String sysname){
        SystemParameter systemParameter = systemParameterRepository.selectBysname(sysname);
        return new BaseResponse(HttpStatus.OK.value(), "当前系统参数", systemParameter);
    }

    public BaseResponse delSysParamsById(Integer sid){
        int res = systemParameterRepository.deleteByPrimaryKey(sid);
        if(res==-1)
            return new BaseResponse(500, "删除失败","");
        return new BaseResponse(HttpStatus.OK.value(), "删除成功", "");
    }

    public BaseResponse updateSysParamsByCode(JSONObject sysparameter){
        Integer sid = sysparameter.getInteger("sysid");
        String sysname = sysparameter.getString("sysname");
        String sysvalue = sysparameter.getString("sysvalue");

        SystemParameter systemParameter = new SystemParameter();
        systemParameter.setSysParameter(sysvalue);
        systemParameter.setSysId(sid);

        if (systemParameterRepository.selectBysname(sysname) != null){
            int success = systemParameterRepository.updateByPrimaryKeySelective(systemParameter);
            if(success != 0){
                return new BaseResponse(HttpStatus.OK.value(), "系统参数更新成功", "Success");
            }else{
                return new BaseResponse(HttpStatus.OK.value(), "系统参数更新失败", "Fail");
            }
        }else {
            return new BaseResponse(HttpStatus.OK.value(), "系统参数不存在", "Exist");
        }
    }

    public BaseResponse getAllSysParams(){
        List<SystemParameter> sysparams = systemParameterRepository.getAllSysParams();
        return new BaseResponse(HttpStatus.OK.value(), "系统参数", sysparams);
    }
}
