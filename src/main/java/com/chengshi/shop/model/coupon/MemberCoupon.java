package com.chengshi.shop.model.coupon;

import java.math.BigDecimal;
import java.util.Date;

public class MemberCoupon {
    private Integer memberCouponId;

    private Integer couponId;

    private Integer memberId;

    private String couponName;

    private Boolean couponType;

    private BigDecimal money;

    private BigDecimal needMoney;

    private Boolean status;

    private Date useStart;

    private Date useEnd;

    private String content;

    private Date getTime;

    private Boolean getType;

    public Integer getMemberCouponId() {
        return memberCouponId;
    }

    public void setMemberCouponId(Integer memberCouponId) {
        this.memberCouponId = memberCouponId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public Boolean getGetType() {
        return getType;
    }

    public void setGetType(Boolean getType) {
        this.getType = getType;
    }
}