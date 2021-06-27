package com.fzu.gcxl.daocloud.application.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

@Service
public interface CheckinService {

    // 发起签到
    public BaseResponse teastartCheckin(@RequestBody JSONObject tcheckin);
    // 结束签到
    public BaseResponse teaendCheckin(@RequestBody JSONObject tcheckin);

    // 学生参与签到
    public BaseResponse stuCheckin(@RequestBody JSONObject tcheckin);

    // 教师获取某门课的签到记录
    public BaseResponse recordCheckinteacher(@RequestBody JSONObject tcheckin);

    //-->> 修改 教师获取全部的签到记录
    public BaseResponse CheckinHistoryteacher(@RequestBody JSONObject tcheckin);

    // 学生 获取签到记录  -->> 修改 学生签到记录 （已签到 未签到）
    public BaseResponse recordCheckinstudent(@RequestBody JSONObject tcheckin);

    // 教师修改签到记录
    public BaseResponse recordModifiedByTeacher(@RequestBody JSONObject tcheckin);

    // 教师修改签到记录
    public BaseResponse stuexp(@RequestBody JSONObject tcheckin);

    public BaseResponse getCheckinfo(String cinfo);

    public BaseResponse stuexpall(JSONObject tcheckin);




}
