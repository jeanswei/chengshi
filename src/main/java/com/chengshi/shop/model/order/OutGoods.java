package com.chengshi.shop.model.order;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 有效商品对象
 *
 * @author yuanrs
 * @date 创建时间：2015年10月12日 下午2:39:51 *
 */
public class OutGoods implements Serializable {
    private static final long serialVersionUID = -6672471404872378793L;
    // 商品 ID
    private Integer goodsId;
    // 货品Id
    private Integer productId;
    // 标题（商品上架名称）
    private String goodsName;
    // 购物车商品图片
    private String goodsImage;
    // 货品库存
    private Integer store;
    // 销售价
    private BigDecimal price = BigDecimal.ZERO;
    // 市场价
    private BigDecimal marktPrice = BigDecimal.ZERO;
    // 货品数量
    private Integer productNum;
    // 是否关注商品
    private Boolean isFollow;
    // 商品是否有效
    private Boolean isValid;
    // 商品金额总计
    private BigDecimal tradeAmount = BigDecimal.ZERO;
    //规格
    private String specView;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getSpecView() {
        return specView;
    }

    public void setSpecView(String specView) {
        this.specView = specView;
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

    public BigDecimal getMarktPrice() {
        return marktPrice;
    }

    public void setMarktPrice(BigDecimal marktPrice) {
        this.marktPrice = marktPrice;
    }

    public Boolean getFollow() {
        return isFollow;
    }

    public void setFollow(Boolean follow) {
        isFollow = follow;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }
}
