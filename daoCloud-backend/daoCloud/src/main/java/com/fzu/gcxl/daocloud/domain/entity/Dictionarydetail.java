package com.fzu.gcxl.daocloud.domain.entity;

import java.util.Date;

public class Dictionarydetail {
    private String dicdetailCode;

    private Integer dictionarydetailId;

    private String dicdetailName;

    private String dicdetailDescription;

    private String dicdetailValue;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String dicCode;

    public String getDicdetailCode() {
        return dicdetailCode;
    }

    public void setDicdetailCode(String dicdetailCode) {
        this.dicdetailCode = dicdetailCode == null ? null : dicdetailCode.trim();
    }

    public Integer getDictionarydetailId() {
        return dictionarydetailId;
    }

    public void setDictionarydetailId(Integer dictionarydetailId) {
        this.dictionarydetailId = dictionarydetailId;
    }

    public String getDicdetailName() {
        return dicdetailName;
    }

    public void setDicdetailName(String dicdetailName) {
        this.dicdetailName = dicdetailName == null ? null : dicdetailName.trim();
    }

    public String getDicdetailDescription() {
        return dicdetailDescription;
    }

    public void setDicdetailDescription(String dicdetailDescription) {
        this.dicdetailDescription = dicdetailDescription == null ? null : dicdetailDescription.trim();
    }

    public String getDicdetailValue() {
        return dicdetailValue;
    }

    public void setDicdetailValue(String dicdetailValue) {
        this.dicdetailValue = dicdetailValue == null ? null : dicdetailValue.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode == null ? null : dicCode.trim();
    }
}