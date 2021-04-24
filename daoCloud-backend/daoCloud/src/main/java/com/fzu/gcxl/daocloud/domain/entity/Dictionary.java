package com.fzu.gcxl.daocloud.domain.entity;

public class Dictionary {
    private String dicCode;

    private Integer dicId;

    private String dicName;

    private String dicDiscription;

    private String dicDetailcode;

    private String dicDetailname;

    private String dicDetaildescription;

    private String dicValue;

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode == null ? null : dicCode.trim();
    }

    public Integer getDicId() {
        return dicId;
    }

    public void setDicId(Integer dicId) {
        this.dicId = dicId;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName == null ? null : dicName.trim();
    }

    public String getDicDiscription() {
        return dicDiscription;
    }

    public void setDicDiscription(String dicDiscription) {
        this.dicDiscription = dicDiscription == null ? null : dicDiscription.trim();
    }

    public String getDicDetailcode() {
        return dicDetailcode;
    }

    public void setDicDetailcode(String dicDetailcode) {
        this.dicDetailcode = dicDetailcode == null ? null : dicDetailcode.trim();
    }

    public String getDicDetailname() {
        return dicDetailname;
    }

    public void setDicDetailname(String dicDetailname) {
        this.dicDetailname = dicDetailname == null ? null : dicDetailname.trim();
    }

    public String getDicDetaildescription() {
        return dicDetaildescription;
    }

    public void setDicDetaildescription(String dicDetaildescription) {
        this.dicDetaildescription = dicDetaildescription == null ? null : dicDetaildescription.trim();
    }

    public String getDicValue() {
        return dicValue;
    }

    public void setDicValue(String dicValue) {
        this.dicValue = dicValue == null ? null : dicValue.trim();
    }
}