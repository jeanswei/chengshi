package com.chengshi.shop.model.goods;

import java.util.List;

public class GoodsSpec {
    private Integer specId;

    private String specName;

    /***********************/
    private List<GoodsSpecValue> specValueList;

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName == null ? null : specName.trim();
    }

    public List<GoodsSpecValue> getSpecValueList() {
        return specValueList;
    }

    public void setSpecValueList(List<GoodsSpecValue> specValueList) {
        this.specValueList = specValueList;
    }
}