package com.fzu.gcxl.daocloud.domain.entity;

import java.util.Date;

public class CheckinHistory {
    private Integer checkinHid;

    private String userId;

    private Date checkinDate;

    private Integer checkinType;

    private Integer checkinNum;

    private Integer checkinNumrequired;

    private String classCode;

    public Integer getCheckinHid() {
        return checkinHid;
    }

    public void setCheckinHid(Integer checkinHid) {
        this.checkinHid = checkinHid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Integer getCheckinType() {
        return checkinType;
    }

    public void setCheckinType(Integer checkinType) {
        this.checkinType = checkinType;
    }

    public Integer getCheckinNum() {
        return checkinNum;
    }

    public void setCheckinNum(Integer checkinNum) {
        this.checkinNum = checkinNum;
    }

    public Integer getCheckinNumrequired() {
        return checkinNumrequired;
    }

    public void setCheckinNumrequired(Integer checkinNumrequired) {
        this.checkinNumrequired = checkinNumrequired;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode == null ? null : classCode.trim();
    }
}