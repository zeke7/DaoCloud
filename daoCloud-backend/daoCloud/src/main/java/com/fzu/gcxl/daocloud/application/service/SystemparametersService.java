package com.fzu.gcxl.daocloud.application.service;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public interface SystemparametersService {

    public BaseResponse createNewSysParams(JSONObject sysparameter);

    public BaseResponse getSysParamsByName(String sysname);

    public BaseResponse delSysParamsById(Integer sid);

    public BaseResponse updateSysParamsByCode(JSONObject sysparameter);

    public BaseResponse getAllSysParams();
}
