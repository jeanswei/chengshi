package com.chengshi.shop.dao.coupon;

import com.chengshi.shop.model.coupon.MemberCoupon;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCouponMapper {
    int deleteByPrimaryKey(Integer memberCouponId);

    int insert(MemberCoupon record);

    int insertSelective(MemberCoupon record);

    MemberCoupon selectByPrimaryKey(Integer memberCouponId);

    int updateByPrimaryKeySelective(MemberCoupon record);

    int updateByPrimaryKey(MemberCoupon record);
}