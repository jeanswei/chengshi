package com.chengshi.shop.dao.goods;

import com.chengshi.shop.model.goods.GoodsSpec;

public interface GoodsSpecMapper {
    int deleteByPrimaryKey(Integer specId);

    int insert(GoodsSpec record);

    int insertSelective(GoodsSpec record);

    GoodsSpec selectByPrimaryKey(Integer specId);

    int updateByPrimaryKeySelective(GoodsSpec record);

    int updateByPrimaryKey(GoodsSpec record);
}