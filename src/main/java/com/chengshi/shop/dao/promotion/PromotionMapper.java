package com.chengshi.shop.dao.promotion;

import com.chengshi.shop.model.promotion.Promotion;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface PromotionMapper {
    int deleteByPrimaryKey(Integer promotionId);

    int insert(Promotion record);

    int insertSelective(Promotion record);

    Promotion selectByPrimaryKey(Integer promotionId);

    int updateByPrimaryKeySelective(Promotion record);

    int updateByPrimaryKey(Promotion record);

    /**
     * 查询促销活动列表
     * @param inMap
     * @return
     */
    List<Promotion> getPromotionList(HashMap<String, Object> inMap);

    /**
     * 查询商品参与的促销活动
     * @param goodsId
     * @return
     */
    Promotion getPromotionByGoodsId(Integer goodsId);
}