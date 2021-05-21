package com.fzu.gcxl.daocloud.application.service;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.domain.entity.Class;
import com.fzu.gcxl.daocloud.domain.entity.ClassInfo;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.repository.ClassInfoRepository;
import com.fzu.gcxl.daocloud.domain.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface ClassService {

    public BaseResponse createNewClass(JSONObject createClass);

    public BaseResponse getClassesByCode(String classCode);

    public BaseResponse getClassesByUser(String userphone);

    public BaseResponse delClassByCode(String classcode);

    public BaseResponse updateClassByCode(JSONObject createClass);

    public void updateClassMemberByCode(JSONObject createClass);

    public BaseResponse closeClassByCode(JSONObject closeClass);

    public BaseResponse ClassNotAllowedByCode(JSONObject closeClass);

    public BaseResponse joinClassByCode(JSONObject joinClass);
    // 获取所有的班课
    public BaseResponse selectAllClasses();

    public BaseResponse selectAllClassesInfo(String userphone);

    public BaseResponse selectAllInfo();

}
