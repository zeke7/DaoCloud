package com.fzu.gcxl.daocloud.frontinterface.controller;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.CheckinService;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.exception.UserFriendException;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckinController {

    @Autowired
    CheckinService checkinService;

    // 教师发起签到
    @CrossOrigin
    @PostMapping("/checkinteachers")
//    @RequiresRoles("teacher")
    public BaseResponse startCheckin(@RequestBody JSONObject tcheckin){
        return checkinService.teastartCheckin(tcheckin);
    }
    // 关闭签到
    @CrossOrigin
    @PutMapping("/checkinover")
//    @RequiresRoles("teacher")
    public BaseResponse endCheckin(@RequestBody JSONObject checkkinover){
        return checkinService.teaendCheckin(checkkinover);
    }

    // 学生参与签到
    @CrossOrigin
    @PostMapping("/checkninstudents")
    public BaseResponse studentCheckin(@RequestBody JSONObject scheckin){
        return checkinService.stuCheckin(scheckin);
    }

    // 获取个人签到记录
    @CrossOrigin
    @PostMapping("/tcheckninrecords")
    public BaseResponse tCheckinRecords(@RequestBody JSONObject checkinrecord){
        return checkinService.recordCheckinteacher(checkinrecord);
    }

    // 获取个人签到记录
    @CrossOrigin
    @PostMapping("/scheckninrecords")
    public BaseResponse sCheckinRecords(@RequestBody JSONObject checkinrecord){
        return checkinService.recordCheckinstudent(checkinrecord);
    }

    @CrossOrigin
    @PutMapping("/makeupscheckninrecords")
    public BaseResponse sCheckinRecordsmakeup(@RequestBody JSONObject checkinrecord){
        return checkinService.recordModifiedByTeacher(checkinrecord);
    }
}
