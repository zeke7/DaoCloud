package com.fzu.gcxl.daocloud.domain.entity;

public class Menu {
    private Integer menuId;

    private String menunameEn;

    private String menunameZh;

    private Integer orderNum;

    private String url;

    private String icon;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenunameEn() {
        return menunameEn;
    }

    public void setMenunameEn(String menunameEn) {
        this.menunameEn = menunameEn == null ? null : menunameEn.trim();
    }

    public String getMenunameZh() {
        return menunameZh;
    }

    public void setMenunameZh(String menunameZh) {
        this.menunameZh = menunameZh == null ? null : menunameZh.trim();
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }
}