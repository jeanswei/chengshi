package com.chengshi.shop.service.coupon;

import com.chengshi.shop.model.coupon.Coupon;
import com.chengshi.shop.model.coupon.CouponGoods;

import java.util.HashMap;
import java.util.List;

/**
 * 优惠券接口
 * @author xuxinlong
 * @version 2017年09月11日
 */
public interface CouponService {

    /**
     * 优惠券列表
     * @param inMap
     * @return
     */
    List<Coupon> getCouponList(HashMap<String, Object> inMap);

    /**
     * 保存优惠券信息
     * @param coupon
     */
    void saveCoupon(Coupon coupon);

    /**
     * 获取优惠券信息
     * @param couponId
     * @return
     */
    Coupon getCoupon(Integer couponId);

    /**
     * 获取优惠券商品列表
     * @param couponId
     * @return
     */
    List<CouponGoods> getCouponGoodsList(Integer couponId);
}
