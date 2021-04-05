package com.fzu.gcxl.daocloud.controller;

import com.fzu.gcxl.daocloud.service.AccountService;
import com.fzu.gcxl.daocloud.service.SendSmsService;

import com.fzu.gcxl.daocloud.service.implemention.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class SmsApiController {

    @Autowired
    private SendSmsService sendSms;

    public String code(){
        return null;
    }

}
