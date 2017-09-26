package com.chengshi.shop.model.goods;

import java.math.BigDecimal;
import java.util.List;

public class GoodsProduct {
    private Integer productId;

    private Integer goodsId;

    private String specImage;

    private String specView;

    private BigDecimal marktPrice;

    private BigDecimal price;

    private Integer store;

    private Boolean isDelete;

    private String specIdAndValueId;

    /*************************/
    private List<String> specValueList;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getSpecImage() {
        return specImage;
    }

    public void setSpecImage(String specImage) {
        this.specImage = specImage == null ? null : specImage.trim();
    }

    public String getSpecView() {
        return specView;
    }

    public void setSpecView(String specView) {
        this.specView = specView == null ? null : specView.trim();
    }

    public BigDecimal getMarktPrice() {
        return marktPrice;
    }

    public void setMarktPrice(BigDecimal marktPrice) {
        this.marktPrice = marktPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getSpecIdAndValueId() {
        return specIdAndValueId;
    }

    public void setSpecIdAndValueId(String specIdAndValueId) {
        this.specIdAndValueId = specIdAndValueId;
    }

    public List<String> getSpecValueList() {
        return specValueList;
    }

    public void setSpecValueList(List<String> specValueList) {
        this.specValueList = specValueList;
    }
}