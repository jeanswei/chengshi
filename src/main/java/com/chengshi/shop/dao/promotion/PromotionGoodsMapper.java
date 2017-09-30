package com.chengshi.shop.dao.promotion;

import com.chengshi.shop.model.promotion.PromotionGoods;

public interface PromotionGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PromotionGoods record);

    int insertSelective(PromotionGoods record);

    PromotionGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PromotionGoods record);

    int updateByPrimaryKey(PromotionGoods record);
}