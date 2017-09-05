package com.chengshi.shop.service.goods;

import com.chengshi.shop.model.goods.Goods;

import java.util.HashMap;
import java.util.List;

/**
 * 商品相关接口
 * @author xuxinlong
 * @version 2017年09月04日
 */
public interface GoodsService {

    /**
     * 查询商品列表
     * @param inMap
     * @return
     */
    List<Goods> getGoodsList(HashMap<String, Object> inMap);

    /**
     * 获取商品详情
     * @param goodsId
     * @return
     */
    Goods getGoodsByGoodsId(Integer goodsId);

    /**
     * 逻辑删除商品
     * @param goodsId
     */
    void deleteGoods(Integer goodsId);
}
