package com.chengshi.shop.model.promotion;

import java.math.BigDecimal;

public class PromotionRule {
    private Integer ruleId;

    private Integer promotionId;

    private BigDecimal needMoney;

    private BigDecimal discount;

    private Integer giveCoupon;

    private Boolean isGiveGift;

    private String gift;

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public BigDecimal getNeedMoney() {
        return needMoney;
    }

    public void setNeedMoney(BigDecimal needMoney) {
        this.needMoney = needMoney;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getGiveCoupon() {
        return giveCoupon;
    }

    public void setGiveCoupon(Integer giveCoupon) {
        this.giveCoupon = giveCoupon;
    }

    public Boolean getIsGiveGift() {
        return isGiveGift;
    }

    public void setIsGiveGift(Boolean isGiveGift) {
        this.isGiveGift = isGiveGift;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift == null ? null : gift.trim();
    }
}