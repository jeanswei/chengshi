package com.chengshi.shop.model.index;

import java.util.Date;

public class IndexFloor {
    private Integer floorId;

    private String name;

    private Integer goodsId;

    private String floorImg;

    private Integer sort;

    private Date createTime;

    /********************/
    private String goodsName;

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getFloorImg() {
        return floorImg;
    }

    public void setFloorImg(String floorImg) {
        this.floorImg = floorImg == null ? null : floorImg.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}