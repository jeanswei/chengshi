package com.chengshi.shop.model.cart;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * 购物车商品对象
 *
 * @author yuanrs
 * @date 创建时间：2015年10月12日 下午2:39:51
 */
public class CartGoods {
    //商品 ID
    private Integer goodsId;
    //货品Id
    private Integer productId;
    //标题（商品上架名称）
    private String goodsName;
    //购物车商品图片
    private String goodsImage;
    //货品库存
    private Integer store;
    //具体的规格字段
    private String specView;
    //市场价
    private BigDecimal marktPrice = BigDecimal.ZERO;
    //销售价
    private BigDecimal price = BigDecimal.ZERO;
    //货品数量
    private Integer productNum;
    //商品总额
    private BigDecimal totalMoney = BigDecimal.ZERO;
    //商品是否有效  0无效 1有效 2受限制
    private Integer isValid;
    //商品无效原因
    private String noValReason;
    //商品类型
    private Byte goodsType = 1;
    //选中状态，0未选中，1选中
    private Byte chooseType;
    //最小购买数量
    private Integer minNum = 1;
    //最大购买数量
    private Integer maxNum = 9999;
    //满减金额
    private BigDecimal promotionMoney = BigDecimal.ZERO;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public BigDecimal getPrice() {
        return price == null ? price : price.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public BigDecimal getMarktPrice() {
        return marktPrice;
    }

    public void setMarktPrice(BigDecimal marktPrice) {
        this.marktPrice = marktPrice;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public String getSpecView() {
        return specView;
    }

    public void setSpecView(String specView) {
        this.specView = specView;
    }

    public String getNoValReason() {
        return noValReason;
    }

    public void setNoValReason(String noValReason) {
        this.noValReason = noValReason;
    }

    public Byte getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Byte goodsType) {
        this.goodsType = goodsType;
    }

    public Byte getChooseType() {
        return chooseType;
    }

    public void setChooseType(Byte chooseType) {
        this.chooseType = chooseType;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getPromotionMoney() {
        return promotionMoney;
    }

    public void setPromotionMoney(BigDecimal promotionMoney) {
        this.promotionMoney = promotionMoney;
    }
}
