package com.fzu.gcxl.daocloud.entity.response;

import java.util.HashMap;
import java.util.Map;


public class ErrorMsg {

    // 状态码
    private int status;
    // 提示信息
    private String message;

    // 封装有效数据
    private Map<String, Object> data = new HashMap<String, Object>();

    public static ErrorMsg success() {
        ErrorMsg result = new ErrorMsg();
        result.setStatus(200);
        result.setMessage("success");
        return result;
    }

    public static ErrorMsg fail() {
        ErrorMsg result = new ErrorMsg();
        result.setStatus(400);
        result.setMessage("fail");
        return result;
    }

    public static ErrorMsg noPermission() {
        ErrorMsg result = new ErrorMsg();
        result.setStatus(401);
        result.setMessage("no permission");
        return result;
    }

    public static ErrorMsg error() {
        ErrorMsg result = new ErrorMsg();
        result.setStatus(500);
        result.setMessage("error");
        return result;
    }

    public static ErrorMsg code(int code){
        ErrorMsg result = new ErrorMsg();
        result.setStatus(code);
        result.setMessage("exception");
        return result;
    }

    public ErrorMsg add(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
