package com.chengshi.shop.service.coupon.impl;

import com.chengshi.shop.dao.coupon.CouponMapper;
import com.chengshi.shop.model.coupon.Coupon;
import com.chengshi.shop.service.coupon.CouponService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author xuxinlong
 * @version 2017年09月11日
 */
@Service
public class CouponServiceImpl implements CouponService {
    @Resource
    private CouponMapper couponMapper;

    /**
     * 优惠券列表
     *
     * @param inMap
     * @return
     */
    @Override
    public List<Coupon> getCouponList(HashMap<String, Object> inMap) {
        return couponMapper.getList(inMap);
    }
}