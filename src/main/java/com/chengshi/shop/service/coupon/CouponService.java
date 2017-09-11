package com.chengshi.shop.service.coupon;

import com.chengshi.shop.model.coupon.Coupon;

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
}
