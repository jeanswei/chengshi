package com.chengshi.shop.service.goods;


import com.chengshi.shop.model.goods.GoodsEvaluate;

import java.util.List;

/**
 * 商品评价相关接口
 * @author xuxinlong
 * @version 2017年07月24日
 */
public interface GoodsEvaluateService {
    /**
     * 查询商品评价数据（0全部，1好评，2中评，3差评）
     * @param goodsId
     * @param score
     * @return
     */
    List<GoodsEvaluate> getEvaluateList(Integer goodsId, Byte score);

    /**
     * 保存商品评价
     * @param evaluateList
     * @param orderId
     * @param memberId
     */
    void saveGoodsEvaluate(List<GoodsEvaluate> evaluateList, Integer orderId, Integer memberId);
}
