package com.chengshi.shop.model.cart;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 购物车明细
 *
 * @author yuanrs
 * @date 创建时间：2015年10月11日 上午10:41:51 *
 */
public class CartItem {
    // 会员id
    private Integer memberId;
    // 加入购物车的供应商货品id
    private Integer productId;
    // 货品的数量
    private int productNum;
    // 选中状态，0未选中，1选中
    private Byte chooseType;
    // 商品是否有效 1有效 0无效
    private Integer isValid = 1;

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

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public Byte getChooseType() {
        return chooseType;
    }

    public void setChooseType(Byte chooseType) {
        this.chooseType = chooseType;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
