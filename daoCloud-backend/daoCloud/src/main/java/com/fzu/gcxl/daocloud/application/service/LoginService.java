package com.fzu.gcxl.daocloud.application.service;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;


@Service
public interface LoginService {

    public BaseResponse login(@RequestBody JSONObject usertologin, HttpServletResponse httpServletResponse);

    public BaseResponse loginsms(@RequestBody JSONObject usertologin, HttpServletResponse httpServletResponse);

}
