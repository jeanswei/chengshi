package com.chengshi.shop.model.coupon;

import java.math.BigDecimal;
import java.util.Date;

public class Coupon {
    private Integer couponId;

    private String couponName;

    private Boolean couponType;

    private BigDecimal money;

    private BigDecimal needMoney;

    private Short totalCount;

    private Short getCount;

    private Date useStart;

    private Date useEnd;

    private String content;

    private Integer limitGrade;

    private Short limitNum;

    private Boolean isShow;

    private Date createTime;

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName == null ? null : couponName.trim();
    }

    public Boolean getCouponType() {
        return couponType;
    }

    public void setCouponType(Boolean couponType) {
        this.couponType = couponType;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getNeedMoney() {
        return needMoney;
    }

    public void setNeedMoney(BigDecimal needMoney) {
        this.needMoney = needMoney;
    }

    public Short getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Short totalCount) {
        this.totalCount = totalCount;
    }

    public Short getGetCount() {
        return getCount;
    }

    public void setGetCount(Short getCount) {
        this.getCount = getCount;
    }

    public Date getUseStart() {
        return useStart;
    }

    public void setUseStart(Date useStart) {
        this.useStart = useStart;
    }

    public Date getUseEnd() {
        return useEnd;
    }

    public void setUseEnd(Date useEnd) {
        this.useEnd = useEnd;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getLimitGrade() {
        return limitGrade;
    }

    public void setLimitGrade(Integer limitGrade) {
        this.limitGrade = limitGrade;
    }

    public Short getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Short limitNum) {
        this.limitNum = limitNum;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}