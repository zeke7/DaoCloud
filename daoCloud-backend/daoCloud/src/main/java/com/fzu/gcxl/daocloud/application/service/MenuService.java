package com.fzu.gcxl.daocloud.application.service;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public interface MenuService {

    public BaseResponse createNewMenu(JSONObject menu);

    public BaseResponse deleteMenuById(Integer mid);

    public BaseResponse updateMenu(JSONObject menu);

    public BaseResponse createNewSubMenu(JSONObject menu);

    public BaseResponse deleteSubMenuById(Integer mid);

    public BaseResponse updateSubMenu(JSONObject menu);
}
