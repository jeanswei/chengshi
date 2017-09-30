package com.chengshi.shop.model.cart;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * 购物车对象
 *
 * @author yuanrs
 * @date 创建时间：2015年10月11日 上午10:41:22 *
 */
public class Cart {

    /**
     * 会员id
     */
    private Integer memberId;

    /**
     * 商品总金额
     */
    private BigDecimal totalMoney = BigDecimal.ZERO;

    /**
     * 商品实际成交额：商品金额总计-促销活动的金额
     */
    private BigDecimal tradeMoney = BigDecimal.ZERO;

    /**
     * 促销金额
     */
    private BigDecimal promotionMoney = BigDecimal.ZERO;

    /**
     * 加入购物车的货品productId，逗号隔开
     */
    private String productIds;

    //是否全选
    private Byte isChooseAll;

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getPromotionMoney() {
        return promotionMoney;
    }

    public void setPromotionMoney(BigDecimal promotionMoney) {
        this.promotionMoney = promotionMoney;
    }

    public Byte getIsChooseAll() {
        return isChooseAll;
    }

    public void setIsChooseAll(Byte isChooseAll) {
        this.isChooseAll = isChooseAll;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(BigDecimal tradeMoney) {
        this.tradeMoney = tradeMoney;
    }
}
