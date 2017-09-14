package com.chengshi.shop.dao.goods;

import com.chengshi.shop.model.goods.GoodsSpecValue;

public interface GoodsSpecValueMapper {
    int deleteByPrimaryKey(Integer specValueId);

    int insert(GoodsSpecValue record);

    int insertSelective(GoodsSpecValue record);

    GoodsSpecValue selectByPrimaryKey(Integer specValueId);

    int updateByPrimaryKeySelective(GoodsSpecValue record);

    int updateByPrimaryKey(GoodsSpecValue record);
}