package com.chengshi.shop.dao.goods;

import com.chengshi.shop.model.goods.GoodsProduct;

public interface GoodsProductMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(GoodsProduct record);

    int insertSelective(GoodsProduct record);

    GoodsProduct selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(GoodsProduct record);

    int updateByPrimaryKey(GoodsProduct record);
}