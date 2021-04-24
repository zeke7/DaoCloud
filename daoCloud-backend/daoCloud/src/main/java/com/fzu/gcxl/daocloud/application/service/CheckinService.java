package com.fzu.gcxl.daocloud.application.service;

import com.alibaba.fastjson.JSONObject;
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

    // 学生 获取签到记录
    public BaseResponse recordCheckinstudent(@RequestBody JSONObject tcheckin);

}
