package com.chengshi.shop.model.goods;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

public class GoodsSearchBean {
    @Field
    private String id; // 主键 格式g_goodsId
    @Field
    private int goodsId; // 产品id
    @Field
    private String goodsName; // 产品名称 商品name
    @Field
    private int catId; // 产品分类id
    @Field
    private String catName; // 产品分类名称
    @Field
    private float marktPrice; // 产品市场价
    @Field
    private float price; // 产品价格
    @Field
    private String[] Keywords; // 关键字
    @Field
    private String[] labelName; // 商品标签
    @Field
    private String goodsImage; // 图片
    @Field
    private int saleCount; // 销量
    @Field
    private int evaluateCount; // 评价数量
    @Field
    private Date createTime; // 创建时间
    @Field
    private Boolean isOnSale; // 上下架状态1.上架2.下架


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String[] getLabelName() {
        return labelName;
    }

    public void setLabelName(String[] labelName) {
        this.labelName = labelName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String[] getKeywords() {
        return Keywords;
    }

    public void setKeywords(String[] keywords) {
        Keywords = keywords;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCountt(int saleCount) {
        this.saleCount = saleCount;
    }

    public int getEvaluateCount() {
        return evaluateCount;
    }

    public void setEvaluateCount(int evaluateCount) {
        this.evaluateCount = evaluateCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public float getMarktPrice() {
        return marktPrice;
    }

    public void setMarktPrice(float marktPrice) {
        this.marktPrice = marktPrice;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public Boolean getOnSale() {
        return isOnSale;
    }

    public void setOnSale(Boolean onSale) {
        isOnSale = onSale;
    }
}
