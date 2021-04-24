package com.fzu.gcxl.daocloud.domain.entity;

import java.util.Date;

public class Class {
    private String classCode;

    private Integer classId;

    private String userPhone;

    private String className;

    private Integer classMember;

    private Date createTime;

    private String classIsclose;

    private String classSemester;

    private String classIsallowed;

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode == null ? null : classCode.trim();
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public Integer getClassMember() {
        return classMember;
    }

    public void setClassMember(Integer classMember) {
        this.classMember = classMember;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getClassIsclose() {
        return classIsclose;
    }

    public void setClassIsclose(String classIsclose) {
        this.classIsclose = classIsclose == null ? null : classIsclose.trim();
    }

    public String getClassSemester() {
        return classSemester;
    }

    public void setClassSemester(String classSemester) {
        this.classSemester = classSemester == null ? null : classSemester.trim();
    }

    public String getClassIsallowed() {
        return classIsallowed;
    }

    public void setClassIsallowed(String classIsallowed) {
        this.classIsallowed = classIsallowed == null ? null : classIsallowed.trim();
    }
}