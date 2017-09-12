package com.chengshi.shop.service.coupon.impl;

import com.chengshi.shop.dao.coupon.CouponGoodsMapper;
import com.chengshi.shop.dao.coupon.CouponMapper;
import com.chengshi.shop.model.coupon.Coupon;
import com.chengshi.shop.model.coupon.CouponGoods;
import com.chengshi.shop.service.coupon.CouponService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 优惠券接口
 * @author xuxinlong
 * @version 2017年09月11日
 */
@Service
public class CouponServiceImpl implements CouponService {
    @Resource
    private CouponMapper couponMapper;
    @Resource
    private CouponGoodsMapper couponGoodsMapper;

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

    /**
     * 保存优惠券信息
     *
     * @param coupon
     */
    @Override
    public void saveCoupon(Coupon coupon) {
        if (coupon.getCouponId() != null) {
            couponMapper.updateByPrimaryKeySelective(coupon);
        } else {
            coupon.setCreateTime(new Date());
            couponMapper.insertSelective(coupon);
        }
    }

    /**
     * 获取优惠券信息
     *
     * @param couponId
     * @return
     */
    @Override
    public Coupon getCoupon(Integer couponId) {
        return couponMapper.selectByPrimaryKey(couponId);
    }

    /**
     * 获取优惠券商品列表
     *
     * @param couponId
     * @return
     */
    @Override
    public List<CouponGoods> getCouponGoodsList(Integer couponId) {
        return couponGoodsMapper.getList(couponId);
    }
}
