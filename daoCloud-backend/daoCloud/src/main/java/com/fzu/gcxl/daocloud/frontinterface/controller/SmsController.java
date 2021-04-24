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


    @GetMapping("/verifiedcodes")
    public BaseResponse getcode(String userPhone){
        return  smsService.sendSms(userPhone);
    }

    @PostMapping("/verifiedcodes")
    public BaseResponse vefifycode(@RequestBody JSONObject usersignup){
        return smsService.verifySms(usersignup);
    }


}
