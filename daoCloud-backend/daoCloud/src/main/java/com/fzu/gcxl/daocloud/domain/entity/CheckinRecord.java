package com.fzu.gcxl.daocloud.domain.entity;

import java.util.Date;

public class CheckinRecord {
    private Integer checkinId;

    private Date checkinTime;

    private String checkinLocx;

    private String userId;

    private String classCode;

    private Date checkinStartdate;

    private String teacherId;

    private String checkinLocy;

    public Integer getCheckinId() {
        return checkinId;
    }

    public void setCheckinId(Integer checkinId) {
        this.checkinId = checkinId;
    }

    public Date getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Date checkinTime) {
        this.checkinTime = checkinTime;
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

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode == null ? null : classCode.trim();
    }

    public Date getCheckinStartdate() {
        return checkinStartdate;
    }

    public void setCheckinStartdate(Date checkinStartdate) {
        this.checkinStartdate = checkinStartdate;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId == null ? null : teacherId.trim();
    }

    public String getCheckinLocy() {
        return checkinLocy;
    }

    public void setCheckinLocy(String checkinLocy) {
        this.checkinLocy = checkinLocy == null ? null : checkinLocy.trim();
    }
}