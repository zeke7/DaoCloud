package com.fzu.gcxl.daocloud.domain.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class Checkin {
    private Integer checkinId;

    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date checkinDate;

    private String classCode;

    private Integer duration;

    private Integer type;

    private Integer isClose;

    private Long classMembers;

    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date startTime;

    @JSONField(format =  "yyyy-MM-dd hh:mm:ss")
    private Date endTime;

    private String checkinLocx;

    private String userId;

    private String checkinLocy;

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

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode == null ? null : classCode.trim();
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

    public String getCheckinLocx() {
        return checkinLocx;
    }

    public void setCheckinLocx(String checkinLocx) {
        this.checkinLocx = checkinLocx == null ? null : checkinLocx.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getCheckinLocy() {
        return checkinLocy;
    }

    public void setCheckinLocy(String checkinLocy) {
        this.checkinLocy = checkinLocy == null ? null : checkinLocy.trim();
    }
}