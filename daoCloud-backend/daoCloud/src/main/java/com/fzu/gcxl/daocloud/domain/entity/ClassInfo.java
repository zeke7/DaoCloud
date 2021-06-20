package com.fzu.gcxl.daocloud.domain.entity;

import java.util.Date;

public class ClassInfo {
    private Integer classinfoId;

    private String userName;

    private String userPhone;

    private String classCode;

    private Date createTime;

    private String className;

    public Integer getClassinfoId() {
        return classinfoId;
    }

    public void setClassinfoId(Integer classinfoId) {
        this.classinfoId = classinfoId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode == null ? null : classCode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }
}