package com.chengshi.shop.service.promotion;


import com.chengshi.shop.model.promotion.Promotion;
import com.chengshi.shop.model.promotion.PromotionRule;

import java.util.HashMap;
import java.util.List;

/**
 * 促销接口
 * @author xuxinlong
 * @version 2017年09月15日
 */
public interface PromotionService {

    /**
     * 查询商品参与的促销(前台使用)
     * @param goodsId
     * @return
     */
    List<HashMap<String, Object>> getGoodsPromotionList(Integer goodsId);

    /**
     * 查询促销活动的规则
     * @param promotionId
     * @return
     */
    List<PromotionRule> getPromotionRuleList(Integer promotionId);

    /**
     * 查询商品参与的促销
     * @param goodsId
     * @return
     */
    Promotion getPromotionByGoodsId(Integer goodsId);
}
