package com.chengshi.shop.service.coupon;

import com.chengshi.shop.model.cart.CartGoods;
import com.chengshi.shop.model.coupon.Coupon;
import com.chengshi.shop.model.coupon.CouponGoods;

import java.math.BigDecimal;
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

    /**
     * 订单可以使用的优惠券和专项款
     * @param memberId
     * @param totalMoney
     * @param cartGoodsList
     * @return
     */
    List<HashMap<String, Object>> getCanUseCouponList(Integer memberId, BigDecimal totalMoney, List<CartGoods> cartGoodsList);

    /**
     * 获取可领取的优惠券
     * @param goodsId
     * @return
     */
    List<Coupon> getCanGetCouponList(Integer goodsId);

    /**
     * 用户已经拥有某优惠券的数量
     * @param memberId
     * @param couponId
     * @return
     */
    Integer getHasCountByCouponId(Integer memberId, Integer couponId);

    /**
     * 会员领取优惠券
     * @param memberId
     * @param coupon
     */
    void receiveCoupon(Integer memberId, Coupon coupon);


    /**
     * 检查会员是否可以领取优惠券
     * @param memberId
     * @param coupon
     * @return
     */
    HashMap<String, Object> checkCouponCanGet(Integer memberId, Coupon coupon);
}
