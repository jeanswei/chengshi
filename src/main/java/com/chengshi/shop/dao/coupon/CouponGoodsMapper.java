package com.chengshi.shop.dao.coupon;

import com.chengshi.shop.model.coupon.CouponGoods;

public interface CouponGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CouponGoods record);

    int insertSelective(CouponGoods record);

    CouponGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CouponGoods record);

    int updateByPrimaryKey(CouponGoods record);
}