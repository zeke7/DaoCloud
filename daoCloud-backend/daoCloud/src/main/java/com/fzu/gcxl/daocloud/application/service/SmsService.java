package com.fzu.gcxl.daocloud.application.service;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.github.qcloudsms.SmsSingleSenderResult;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface SmsService {

    // 发送验证码 并存储在redis中
    public BaseResponse sendSms(@RequestBody JSONObject usersms);

    // 对验证码进行验证
    public BaseResponse verifySms(@RequestBody JSONObject usersignup);
}
