package com.chengshi.shop.dao.order;

import com.chengshi.shop.model.order.OrderCoupon;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderCouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderCoupon record);

    int insertSelective(OrderCoupon record);

    OrderCoupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderCoupon record);

    int updateByPrimaryKey(OrderCoupon record);

    /**
     * 获取订单赠送优惠券
     * @param orderId
     * @return
     */
    List<OrderCoupon> getListByOrderId(Integer orderId);
}