package com.fzu.gcxl.daocloud.application.service;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import org.springframework.stereotype.Service;

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

    public BaseResponse selectAllClassesInfoPerson(String userphone);

    public BaseResponse selectAllInfo();

    public BaseResponse getStudentCountsByClassCode(String classCode);

}
