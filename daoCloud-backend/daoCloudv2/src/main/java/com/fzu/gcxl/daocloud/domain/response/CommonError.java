package com.fzu.gcxl.daocloud.domain.response;

public interface CommonError {
    int getErrCode();

    String getErrMsg();

    CommonError setErrMsg(String errMsg);
}
