package com.fzu.gcxl.daocloud.domain.entity;

public class SystemParameter {
    private Integer sysId;

    private String sysName;

    private String sysParameter;

    public Integer getSysId() {
        return sysId;
    }

    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName == null ? null : sysName.trim();
    }

    public String getSysParameter() {
        return sysParameter;
    }

    public void setSysParameter(String sysParameter) {
        this.sysParameter = sysParameter == null ? null : sysParameter.trim();
    }
}