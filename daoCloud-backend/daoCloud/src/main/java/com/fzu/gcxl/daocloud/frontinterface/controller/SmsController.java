package com.fzu.gcxl.daocloud.frontinterface.controller;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.SmsService;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class SmsController {

    @Autowired
    private SmsService smsService;


    // type: "L0""S1""R2" -> "登陆""注册""忘记密码"
    @PostMapping("/verifiedcodes")
    public BaseResponse getcode(@RequestBody JSONObject usersms){
        return  smsService.sendSms(usersms);
    }

    @PostMapping("/verifiedcodesfromuser")
    public BaseResponse vefifycode(@RequestBody JSONObject usersignup){
        return smsService.verifySms(usersignup);
    }


}
