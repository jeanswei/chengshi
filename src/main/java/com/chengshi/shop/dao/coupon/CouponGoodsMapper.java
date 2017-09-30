package com.chengshi.shop.dao.coupon;

import com.chengshi.shop.model.coupon.CouponGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CouponGoods record);

    int insertSelective(CouponGoods record);

    CouponGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CouponGoods record);

    int updateByPrimaryKey(CouponGoods record);

    /**
     * 查询优惠券适用商品列表
     * @param couponId
     * @return
     */
    List<CouponGoods> getList(Integer couponId);

    /**
     * 删除多余的优惠券商品
     * @param couponId
     * @param couponGoodsIds
     */
    void deleteNotInCouponGoodsIds(@Param("couponId") Integer couponId,@Param("couponGoodsIds") String couponGoodsIds);

    /**
     * 优惠券适用的商品id列表
     * @param couponId
     * @return
     */
    List<Integer> getGoodsIdListByCouponId(Integer couponId);
}