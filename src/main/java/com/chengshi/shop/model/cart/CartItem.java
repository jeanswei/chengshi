package com.chengshi.shop.model.cart;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 购物车明细
 *
 * @author 徐新龙
 * @version 2017年4月14日 上午10:57:06
 */
public class CartItem {
    // 会员id
    private Integer memberId;
    // 货品id
    private Integer productId;
    // 货品的数量
    private Integer productNum;
    // 选中状态
    private Boolean isChoose;
    // 商品是否有效
    private Boolean isValid = false;

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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public Boolean getIsChoose() {
        return isChoose;
    }

    public void setIsChoose(Boolean isChoose) {
        this.isChoose = isChoose;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}
