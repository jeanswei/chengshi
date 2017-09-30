package com.chengshi.shop.service.promotion.impl;

import com.chengshi.shop.dao.coupon.CouponMapper;
import com.chengshi.shop.dao.promotion.PromotionMapper;
import com.chengshi.shop.dao.promotion.PromotionRuleMapper;
import com.chengshi.shop.model.coupon.Coupon;
import com.chengshi.shop.model.promotion.Promotion;
import com.chengshi.shop.model.promotion.PromotionRule;
import com.chengshi.shop.service.promotion.PromotionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 促销接口
 *
 * @author xuxinlong
 * @version 2017年09月15日
 */
@Service
public class PromotionServiceImpl implements PromotionService {
    @Resource
    private PromotionMapper promotionMapper;
    @Resource
    private PromotionRuleMapper promotionRuleMapper;
    @Resource
    private CouponMapper couponMapper;


    /**
     * 查询商品参与的促销
     *
     * @param goodsId
     * @return
     */
    @Override
    public List<HashMap<String, Object>> getGoodsPromotionList(Integer goodsId) {
        List<HashMap<String, Object>> mapList = new ArrayList<>();
        StringBuilder discountStr = new StringBuilder();
        StringBuilder couponStr = new StringBuilder();
        StringBuilder giftStr = new StringBuilder();
        Promotion promotion = this.getPromotionByGoodsId(goodsId);
        //商品参与了促销活动
        if (promotion != null) {
            List<PromotionRule> ruleList = this.getPromotionRuleList(promotion.getPromotionId());
            for (PromotionRule promotionRule : ruleList) {
                //满减
                if (promotionRule.getDiscount().compareTo(BigDecimal.ZERO) > 0) {
                    discountStr.append("，满").append(promotionRule.getNeedMoney()).append("元减").append(promotionRule.getDiscount()).append("元");
                }
                //优惠券
                if (promotionRule.getGiveCoupon() > 0) {
                    Coupon coupon = couponMapper.selectByPrimaryKey(promotionRule.getGiveCoupon());
                    if (coupon != null) {
                        couponStr.append("，满").append(promotionRule.getNeedMoney()).append("元送").append(coupon.getMoney()).append("元优惠券");
                    }
                }
                //赠品
                if (promotionRule.getIsGiveGift()) {
                    giftStr.append("，满").append(promotionRule.getNeedMoney()).append("元送").append(promotionRule.getGift());
                }
            }
        }

        if (discountStr.length() > 0) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("label", "满减");
            map.put("text", discountStr.substring(1));
            mapList.add(map);
        }
        if (couponStr.length() > 0) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("label", "赠券");
            map.put("text", couponStr.substring(1));
            mapList.add(map);
        }
        if (giftStr.length() > 0) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("label", "赠品");
            map.put("text", giftStr.substring(1));
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 查询促销活动的规则
     *
     * @param promotionId
     * @return
     */
    @Override
    public List<PromotionRule> getPromotionRuleList(Integer promotionId) {
        return promotionRuleMapper.selectPromotionRuleListByPromotionId(promotionId);
    }

    /**
     * 查询商品参与的促销
     *
     * @param goodsId
     * @return
     */
    @Override
    public Promotion getPromotionByGoodsId(Integer goodsId) {
        return promotionMapper.getPromotionByGoodsId(goodsId);
    }
}
