package com.chengshi.shop.model.category;

import java.util.Date;

public class Category {
    private Short catId;

    private Short parentId;

    private String catName;

    private Integer sort;

    private String catImg;

    private Date createTime;

    public Short getCatId() {
        return catId;
    }

    public void setCatId(Short catId) {
        this.catId = catId;
    }

    public Short getParentId() {
        return parentId;
    }

    public void setParentId(Short parentId) {
        this.parentId = parentId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName == null ? null : catName.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCatImg() {
        return catImg;
    }

    public void setCatImg(String catImg) {
        this.catImg = catImg == null ? null : catImg.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}