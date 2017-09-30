package com.chengshi.shop.model.order;

import java.util.Date;

public class OrderReturnHistory {
    private Integer id;

    private Integer orderReturnId;

    private Integer orderItemId;

    private String content;

    private Date createTime;

    private String opName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderReturnId() {
        return orderReturnId;
    }

    public void setOrderReturnId(Integer orderReturnId) {
        this.orderReturnId = orderReturnId;
    }

    public Integer getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName == null ? null : opName.trim();
    }
}