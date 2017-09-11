package com.chengshi.shop.dao.coupon;

import com.chengshi.shop.model.coupon.Coupon;
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
}