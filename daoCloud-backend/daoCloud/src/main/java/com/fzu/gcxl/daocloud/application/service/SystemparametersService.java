package com.fzu.gcxl.daocloud.application.service;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.domain.entity.Class;
import com.fzu.gcxl.daocloud.domain.entity.SystemParameter;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.repository.SystemParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SystemparametersService {

    public BaseResponse createNewSysParams(JSONObject sysparameter);

    public BaseResponse getSysParamsByName(String sysname);

    public BaseResponse delSysParamsById(Integer sid);

    public BaseResponse updateSysParamsByCode(JSONObject sysparameter);

    public BaseResponse getAllSysParams();
}
