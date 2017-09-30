package com.chengshi.shop.dao.promotion;

import com.chengshi.shop.model.promotion.PromotionRule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRuleMapper {
    int deleteByPrimaryKey(Integer ruleId);

    int insert(PromotionRule record);

    int insertSelective(PromotionRule record);

    PromotionRule selectByPrimaryKey(Integer ruleId);

    int updateByPrimaryKeySelective(PromotionRule record);

    int updateByPrimaryKey(PromotionRule record);

    /**
     * 根据PromotionId查询促销活动规则列表
     * @param promotionId
     * @return
     */
    List<PromotionRule> selectPromotionRuleListByPromotionId(Integer promotionId);

    /**
     * 删除修改后多余的促销规则信息
     * @param promotionId
     * @param ruleIds
     * @return
     */
    int deleteNotInRules(@Param("promotionId") Integer promotionId, @Param("ruleIds") String ruleIds);

    /**
     * 根据promotionId删除促销规则
     * @param promotionId
     * @return
     */
    int deletePromotionRule(Integer promotionId);
}