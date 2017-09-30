package com.chengshi.shop.dao.coupon;

import com.chengshi.shop.model.coupon.Coupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface CouponMapper {
    int deleteByPrimaryKey(Integer couponId);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer couponId);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

    /**
     * 优惠券列表
     * @param inMap
     * @return
     */
    List<Coupon> getList(HashMap<String, Object> inMap);

    /**
     * 商品可领取的优惠券
     * @return
     */
    List<Coupon> getCanGetCouponList(Integer goodsId);

    /**
     * 增加已领取数量
     * @param couponId
     * @param count
     */
    void addGetCount(@Param("couponId") Integer couponId, @Param("count") Integer count);

    /**
     * 更新优惠券状态
     * @param couponId
     * @param status
     */
    void updateStatus(@Param("couponId") Integer couponId,@Param("status") byte status);
}