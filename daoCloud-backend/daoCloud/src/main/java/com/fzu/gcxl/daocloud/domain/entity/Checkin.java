package com.fzu.gcxl.daocloud.domain.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class Checkin {
    private Integer checkinId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date checkinDate;

    private Integer duration;

    private Integer type;

    private Integer isClose;

    private Long classMembers;

    private String classCode;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String checkinLocation;

    private String userId;

    public Integer getCheckinId() {
        return checkinId;
    }

    public void setCheckinId(Integer checkinId) {
        this.checkinId = checkinId;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsClose() {
        return isClose;
    }

    public void setIsClose(Integer isClose) {
        this.isClose = isClose;
    }

    public Long getClassMembers() {
        return classMembers;
    }

    public void setClassMembers(Long classMembers) {
        this.classMembers = classMembers;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode == null ? null : classCode.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCheckinLocation() {
        return checkinLocation;
    }

    public void setCheckinLocation(String checkinLocation) {
        this.checkinLocation = checkinLocation == null ? null : checkinLocation.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}