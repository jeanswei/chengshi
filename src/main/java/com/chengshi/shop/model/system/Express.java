package com.chengshi.shop.model.system;

public class Express {
    private Byte expressId;

    private String expressCode;

    private String expressName;

    public Byte getExpressId() {
        return expressId;
    }

    public void setExpressId(Byte expressId) {
        this.expressId = expressId;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode == null ? null : expressCode.trim();
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName == null ? null : expressName.trim();
    }
}